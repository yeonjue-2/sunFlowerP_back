package io.sunflower.auth.service;

import io.jsonwebtoken.Claims;
import io.sunflower.auth.dto.LoginRequest;
import io.sunflower.auth.dto.LoginResponse;
import io.sunflower.auth.dto.ReissueResponse;
import io.sunflower.auth.dto.SignupRequest;
import io.sunflower.common.exception.model.AuthException;
import io.sunflower.common.exception.model.DuplicatedException;
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

import static io.sunflower.common.exception.ExceptionStatus.*;
import static io.sunflower.security.jwt.JwtTokenProvider.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequest request) {
        String emailId = request.getEmailId();
        String password = passwordEncoder.encode(request.getPassword());
        String nickname = request.getNickname();

        checkIfUserEmailDuplicated(emailId);
        checkIfUserNicknameDuplicated(nickname);

        UserRoleEnum role = UserRoleEnum.USER;

        if (request.isAdmin()) {
            if (!request.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 올바르지 않습니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(request, emailId, password, nickname, role);
        userRepository.save(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        User user = userService.findUserByEmailId(request.getEmailId());
        checkPassword(request.getPassword(), user);

        Authentication authentication = jwtTokenProvider.createAuthentication(request.getEmailId());
        TokenDto tokenDto = jwtTokenProvider.createToken(authentication);

        // Redis - 로그인시 {email: refreshToken} 으로 저장
        redisUtil.setDataExpire(authentication.getName(), tokenDto.getRefreshToken(), REFRESH_TOKEN_TIME);

        response.addHeader(AUTHORIZATION_HEADER, BEARER_PREFIX + tokenDto.getAccessToken());

        return new LoginResponse(user.getEmailId(), tokenDto.getAccessToken(), tokenDto.getRefreshToken());

        // TO-DO
//        return LoginResponse.of(user.getEmailId(), tokenDto.getAccessToken(),
//                tokenDto.getRefreshToken());
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

    // ==================== 내부 메서드 ======================

    /**
     * 회원가입 시 이메일 중복 확인
     * @param emailId
     */
    private void checkIfUserEmailDuplicated(String emailId) {
        if (userRepository.existsByEmailId(emailId)) {
            throw new DuplicatedException(DUPLICATED_EMAIL);
        }
    }

    private void checkIfUserNicknameDuplicated(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new DuplicatedException(DUPLICATED_NICKNAME);
        }
    }

    private void checkPassword(String password, User user) {
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

}
