package io.sunflower.auth.service;

import io.sunflower.auth.dto.LoginRequest;
import io.sunflower.auth.dto.SignupRequest;
import io.sunflower.common.exception.model.AuthException;
import io.sunflower.common.exception.model.DuplicatedException;
import io.sunflower.user.entity.User;
import io.sunflower.entity.enumeration.UserRoleEnum;
import io.sunflower.security.jwt.JwtUtil;
import io.sunflower.user.repository.UserRepository;
import io.sunflower.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.sunflower.common.exception.ExceptionStatus.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

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

    @Transactional(readOnly = true)
    public String login(LoginRequest request) {
        String emailId = request.getEmailId();
        String password = request.getPassword();

        User user = userService.findUserByEmailId(emailId);
        checkPassword(password, user);

        // JWT 활용 시 추가
        return jwtUtil.createToken(user.getEmailId(), user.getRole());
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

}
