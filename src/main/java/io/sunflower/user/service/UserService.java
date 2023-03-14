package io.sunflower.user.service;

import io.sunflower.common.exception.model.InvalidAccessException;
import io.sunflower.common.exception.model.NotFoundException;
import io.sunflower.user.dto.*;
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

    public UserModalInfoResponse findUserInModal(String nickname) {
        User userById = findUserByNickname(nickname);
        return new UserModalInfoResponse(userById);
    }

    @Transactional
    public UserInfoUpdateResponse modifyUserInfo(String userImageUrl, String nickname, UserInfoUpdateRequest request, User user) {

        User userById = findUserByNickname(nickname);

        if (userById.getEmailId().equals(user.getEmailId())) {
            userById.updateUserInfo(request, userImageUrl);
            userRepository.save(userById);
            return new UserInfoUpdateResponse(userById);
        } else {
            throw new InvalidAccessException(NOT_AUTHORIZED_USER);
        }
    }

    public void modifyPassword(String nickname, PasswordUpdateRequest request, User user) {
        User userById = findUserByNickname(nickname);

        if (userById.getEmailId().equals(user.getEmailId()) &&
            passwordEncoder.matches(request.getCurPassword(), user.getPassword())) {
            String password = passwordEncoder.encode(request.getNewPassword());
            userById.updatePassword(password);
            userRepository.save(userById);
        } else {
            throw new InvalidAccessException(NOT_AUTHORIZED_PASSWORD);
        }
    }

    public void reissuePassword(String nickname, ReissuePasswordRequest request, User user) {
        User userById = findUserByNickname(nickname);

        if (userById.getEmailId().equals(user.getEmailId())) {
            String password = passwordEncoder.encode(request.getPassword());
            userById.updatePassword(password);
            userRepository.save(userById);
        } else {
            throw new InvalidAccessException(NOT_AUTHORIZED_PASSWORD);
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
