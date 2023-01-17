package io.sunflower.service;

import io.sunflower.dto.request.LoginRequest;
import io.sunflower.dto.request.SignupRequest;
import io.sunflower.entity.User;
import io.sunflower.entity.enumeration.UserRoleEnum;
import io.sunflower.jwt.JwtUtil;
import io.sunflower.repository.UserRepository;
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
        String personalId = request.getPersonalId();
        String password = passwordEncoder.encode(request.getPassword());
        String nickname = request.getNickname();
        String email = request.getEmail();

        Optional<User> foundById = userRepository.findByPersonalId(personalId);
        if (foundById.isPresent()) {
            throw new IllegalArgumentException("중복된 아이디가 있습니다.");
        }

        Optional<User> foundByNickname = userRepository.findByNickname(nickname);
        if (foundByNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임이 있습니다.");
        }

        Optional<User> foundByEmail = userRepository.findByEmail(email);
        if (foundByEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 있습니다.");
        }

        UserRoleEnum role = UserRoleEnum.USER;

        if (request.isAdmin()) {
            if (!request.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(request, personalId, password, nickname, email, role);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest request) {
        String personalId = request.getPersonalId();
        String password = request.getPassword();

        User user = userRepository.findByPersonalId(personalId).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        // 비밀번호 확인, 자동으로 request에서 가져온 password를 변환해서 비교해 줌.
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 활용 시 추가
        return jwtUtil.createToken(user.getPersonalId(), user.getRole());
    }
}
