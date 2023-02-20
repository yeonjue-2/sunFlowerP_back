package io.sunflower.user.controller;

import io.sunflower.user.dto.UserInfoResponse;
import io.sunflower.user.dto.UserInfoUpdateRequest;
import io.sunflower.security.UserDetailsImpl;
import io.sunflower.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // 사용자의 프로필 수정
    @PatchMapping("/{emailId}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfoResponse updateUserInfo(@PathVariable String emailId, @RequestBody UserInfoUpdateRequest request,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.modifyUserInfo(emailId, request, userDetails.getUser());
    }
}
