package io.sunflower.user.service;

import io.sunflower.user.dto.LoginRequest;
import io.sunflower.user.dto.SignupRequest;
import io.sunflower.user.entity.User;
import io.sunflower.entity.enumeration.UserRoleEnum;
import io.sunflower.security.jwt.JwtUtil;
import io.sunflower.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequest request) {
        String emailId = request.getEmailId();
        String password = passwordEncoder.encode(request.getPassword());
        String nickname = request.getNickname();

        Optional<User> foundById = userRepository.findByEmailId(emailId);
        if (foundById.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 있습니다.");
        }

        Optional<User> foundByNickname = userRepository.findByNickname(nickname);
        if (foundByNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임이 있습니다.");
        }

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

        User user = userRepository.findByEmailId(emailId).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        // 비밀번호 확인, 자동으로 request에서 가져온 password를 변환해서 비교해 줌.
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 활용 시 추가
        return jwtUtil.createToken(user.getEmailId(), user.getRole());
    }
}