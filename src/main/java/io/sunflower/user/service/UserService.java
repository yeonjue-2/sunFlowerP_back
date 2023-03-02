package io.sunflower.user.service;

import io.sunflower.common.exception.model.InvalidAccessException;
import io.sunflower.common.exception.model.NotFoundException;
import io.sunflower.user.dto.UserInfoResponse;
import io.sunflower.user.dto.UserInfoUpdateRequest;
import io.sunflower.user.dto.UserProfileResponse;
import io.sunflower.user.entity.User;
import io.sunflower.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.sunflower.common.exception.ExceptionStatus.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileResponse findUser(String nickname) {
        User userById = findUserByNickname(nickname);
        return new UserProfileResponse(userById);
    }

    @Transactional
    public UserInfoResponse modifyUserInfo(String userImageUrl, String nickname, UserInfoUpdateRequest request, User user) {

        User userById = findUserByNickname(nickname);

        String password = null;
        if (request.getPassword() != null) {
            password = passwordEncoder.encode(request.getPassword());
        }

        if (userById.getEmailId().equals(user.getEmailId())) {
            userById.updateUserInfo(request, password, userImageUrl);
            userRepository.save(userById);
            return new UserInfoResponse(userById);
        } else {
            throw new InvalidAccessException(NOT_AUTHORIZED_USER);
        }
    }


    // ==================== 내부 메서드 ======================

    /**
     * emailId로 User 객체 찾기
     * @param emailId
     */
    public User findUserByEmailId(String emailId) {
        return userRepository.findByEmailId(emailId).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_USER));
    }

    public User findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_USER));
    }

}
