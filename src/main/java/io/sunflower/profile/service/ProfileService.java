package io.sunflower.profile.service;

import io.sunflower.profile.dto.ProfileResponse;
import io.sunflower.profile.dto.ProfileUpdateRequest;
import io.sunflower.user.entity.User;
import io.sunflower.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;

    @Transactional
    public ProfileResponse modifyProfile(Long id, ProfileUpdateRequest request, User user) {

        User profileUser = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다.")
        );

        List<ProfileUpdateRequest> updateRequests = new ArrayList<>();
        updateRequests.add(request);

        if (profileUser.getEmailId().equals(user.getEmailId())) {
            profileUser.updateProfile(updateRequests);
            userRepository.save(profileUser);
            return new ProfileResponse(profileUser);
        } else {
            throw new IllegalArgumentException("접근할 수 있는 권한이 없습니다.");
        }
    }
}
