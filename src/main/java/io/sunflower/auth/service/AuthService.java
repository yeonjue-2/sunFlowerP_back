package io.sunflower.auth.service;

import io.jsonwebtoken.Claims;
import io.sunflower.auth.dto.LoginRequest;
import io.sunflower.auth.dto.LoginResponse;
import io.sunflower.auth.dto.ReissueResponse;
import io.sunflower.auth.dto.SignupRequest;
import io.sunflower.common.enumeration.UserStatus;
import io.sunflower.common.exception.model.AuthException;
import io.sunflower.common.exception.model.DuplicatedException;
import io.sunflower.common.exception.model.NotFoundException;
import io.sunflower.common.util.RedisUtil;
import io.sunflower.security.jwt.JwtTokenProvider;
import io.sunflower.security.jwt.dto.TokenDto;
import io.sunflower.security.jwt.dto.TokenRequest;
import io.sunflower.user.entity.User;
import io.sunflower.common.enumeration.UserRoleEnum;
import io.sunflower.user.repository.UserRepository;
import io.sunflower.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

import static io.sunflower.common.constant.JwtConstant.*;
import static io.sunflower.common.enumeration.UserStatus.*;
import static io.sunflower.common.exception.ExceptionStatus.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    @Transactional
    public void signup(String userImageUrl, SignupRequest request) {
        String emailId = request.getEmailId();
        String password = passwordEncoder.encode(request.getPassword());
        String nickname = request.getNickname();

        checkIfUserEmailIdDuplicated(emailId);
        checkIfUserNicknameDuplicated(nickname);

        UserRoleEnum role = UserRoleEnum.USER;

        User user = new User(emailId, password, nickname, role, userImageUrl);
        userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        User user = userService.findUserByEmailId(request.getEmailId());

        checkUserStatus(user.getStatus());
        checkPassword(request.getPassword(), user);

        Authentication authentication = jwtTokenProvider.createAuthentication(request.getEmailId());
        TokenDto tokenDto = jwtTokenProvider.createToken(authentication);

        // Redis - 로그인시 {email: refreshToken} 으로 저장
        redisUtil.setDataExpire(authentication.getName(), tokenDto.getRefreshToken(), REFRESH_TOKEN_TIME);

        response.addHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + tokenDto.getAccessToken());

        return LoginResponse.of(user.getNickname(), tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    @Transactional
    public ReissueResponse reissue(TokenRequest request, HttpServletResponse response) {
        String emailId = jwtTokenProvider.getUserInfoFromToken(request.getAccessToken()).getSubject();
        validateRefreshToken(request);

        try {
            Authentication authentication = jwtTokenProvider.createAuthentication(emailId);
            String validRefreshToken = redisUtil.getData(emailId);
            validateRefreshTokenOwner(validRefreshToken, request);

            // 새로운 토큰 발행
            TokenDto tokenDto = jwtTokenProvider.createToken(authentication);
            redisUtil.setDataExpire(authentication.getName(), tokenDto.getRefreshToken(), REFRESH_TOKEN_TIME);
            response.addHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + tokenDto.getAccessToken());

            return ReissueResponse.of(tokenDto.getAccessToken(), tokenDto.getRefreshToken());
        } catch (NullPointerException e) {
            throw new AuthException(INVALID_REFRESH_TOKEN);
        }

    }

    @Transactional
    public void logout(TokenRequest request) {
        if (!jwtTokenProvider.validateToken(request.getAccessToken())) {
            throw new AuthException(INVALID_ACCESS_TOKEN);
        }

        Claims claims = jwtTokenProvider.getUserInfoFromToken(request.getAccessToken());
        String emailId = claims.getSubject();
        redisUtil.deleteData(emailId);

        redisUtil.setDataExpire("JWT:ATK:" +request.getAccessToken(), "TRUE", ACCESS_TOKEN_TIME / 1000L);
    }

    @Transactional
    public void signOut(TokenRequest request, User user) {
        user.updateUserStatus();
        userRepository.save(user);
        logout(request);
    }



    // ==================== 내부 메서드 ======================

    /**
     * 회원가입 시 이메일 중복 확인
     * @param emailId
     */
    public void checkIfUserEmailIdDuplicated(String emailId) {
        if (userRepository.existsByEmailId(emailId)) {
            throw new DuplicatedException(DUPLICATED_EMAIL);
        }
    }

    public void checkIfUserNicknameDuplicated(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicatedException(DUPLICATED_NICKNAME);
        }
    }

    public void checkPassword(String password, User user) {
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new AuthException(INVALID_EMAIL_OR_PW);
        }
    }

    private void validateRefreshToken(TokenRequest request) {
        if (!jwtTokenProvider.validateToken(request.getRefreshToken())) {
            throw new AuthException(INVALID_REFRESH_TOKEN);
        }
    }

    private void validateRefreshTokenOwner(String validRefreshToken, TokenRequest request) {
        if (!request.validateToken(validRefreshToken)) {
            throw new AuthException(INVALID_REFRESH_TOKEN);
        }
    }

    private void checkUserStatus(UserStatus status) {
        if (status.equals(DELETED)) {
            throw new NotFoundException(NOT_FOUND_USER);
        }
    }
}
