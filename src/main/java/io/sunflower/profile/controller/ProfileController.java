package io.sunflower.profile.controller;

import io.sunflower.profile.dto.ProfileResponse;
import io.sunflower.profile.dto.ProfileUpdateRequest;
import io.sunflower.profile.service.ProfileService;
import io.sunflower.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/profile")
public class ProfileController {

    private final ProfileService profileService;

    // 사용자의 프로필 수정
    @PatchMapping("/{id}")
    public ProfileResponse updateProfile(@PathVariable Long id, @RequestBody ProfileUpdateRequest request,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return profileService.modifyProfile(id, request, userDetails.getUser());
    }
}
