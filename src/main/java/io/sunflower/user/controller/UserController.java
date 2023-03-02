package io.sunflower.user.controller;

import io.sunflower.s3.S3Uploader;
import io.sunflower.user.dto.UserInfoResponse;
import io.sunflower.user.dto.UserInfoUpdateRequest;
import io.sunflower.security.UserDetailsImpl;
import io.sunflower.user.dto.UserProfileResponse;
import io.sunflower.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static io.sunflower.common.constant.UserConst.DEFAULT_USER_IMAGE;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final S3Uploader s3Uploader;

    /**
     * 사용자의 프로필 수정
     *
     * @param nickname
     * @param request
     * @param userDetails
     * @return UserInfoResponse
     */
    // TO-DO
//    @PatchMapping("/{nickname}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public UserInfoResponse updateUserInfo(@PathVariable String nickname, @RequestBody UserInfoUpdateRequest request,
//                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        String userImageUrl = userDetails.getUser().getUserImageUrl();
//
//        if (!request.getUserImage().equals(userDetails.getUser().getUserImageUrl())) {
//            s3Uploader.deleteFile(userImageUrl, "userImage");
//            s3Uploader.checkFileExtension(request.getUserImage().getOriginalFilename());
//            userImageUrl = s3Uploader.uploadFile(request.getUserImage(), "userImage");
//        }
//
//        return userService.modifyUserInfo(userImageUrl, nickname, request, userDetails.getUser());
//    }


    @PatchMapping("/{nickname}")
    @ResponseStatus(HttpStatus.CREATED)
    public UserInfoResponse updateUserInfo(@PathVariable String nickname, @RequestBody UserInfoUpdateRequest request,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userImageUrl = DEFAULT_USER_IMAGE;
        String curUserImage = userDetails.getUser().getUserImageUrl();

        if (request.getUserImage() != null) {
            s3Uploader.deleteFile(curUserImage, "userImage");
            s3Uploader.checkFileExtension(request.getUserImage().getOriginalFilename());
            userImageUrl = s3Uploader.uploadFile(request.getUserImage(), "userImage");
        }


        return userService.modifyUserInfo(userImageUrl, nickname, request, userDetails.getUser());
    }


    /**
     * nickname 유저의 프로필 보기
     *
     * @param nickname
     * @return
     */
    @GetMapping("/{nickname}")
    @ResponseStatus(HttpStatus.OK)
    public UserProfileResponse readUser(@PathVariable String nickname) {
        return userService.findUser(nickname);
    }
}
