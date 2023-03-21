package io.sunflower.user.controller;

import io.sunflower.s3.S3Uploader;
import io.sunflower.user.dto.*;
import io.sunflower.security.UserDetailsImpl;
import io.sunflower.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static io.sunflower.common.constant.UserConst.DEFAULT_USER_IMAGE;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final S3Uploader s3Uploader;

    /**
     * nickname 유저의 프로필 보기
     *
     * @param nickname
     * @return
     */
    @GetMapping("/users/{nickname}")
    @ResponseStatus(HttpStatus.OK)
    public UserProfileResponse readUser(@PathVariable String nickname) {
        return userService.findUser(nickname);
    }

    /**
     * 유저의 프로필 수정
     *
     * @param nickname
     * @param request
     * @param userDetails
     * @return UserInfoResponse
     */
    @PatchMapping(value = {"/users/{nickname}"}, consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserInfoUpdateResponse updateUserInfo(@PathVariable String nickname, @ModelAttribute UserInfoUpdateRequest request,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String userImageUrl = DEFAULT_USER_IMAGE;
        String curUserImage = userDetails.getUser().getUserImageUrl();

        if (!request.getUserImage().isEmpty()) {
            s3Uploader.deleteFile(curUserImage, "userImage");
            s3Uploader.checkFileExtension(request.getUserImage().getOriginalFilename());
            userImageUrl = s3Uploader.uploadFile(request.getUserImage(), "userImage");
        }

        return userService.modifyUserInfo(userImageUrl, nickname, request, userDetails.getUser());
    }


    /**
     * 유저 이미지의 경우
     * 1. 기본 이미지로 유지할 경우
     *  -> request에 들어오는 값 없음 -> 기본 이미지와 같은 url이므로 업데이트 x
     * 2. 새로운 이미지로 변경할 경우
     *  -> 새로운 이미지 파일이 request에 포함 -> 새로운 이미지로 url로 업데이트
     * 3. 이미 자신의 이미지가 있고 이번 수정에서 이미지는 변경하고 싶지 않을 때
     *  -> request에 들어오는 값 없음 -> 원래 이미지로 그대로 유지, 업데이트 x
     * 4. 이미 자신의 이미지가 있고 그걸 수정을 통해 삭제하고 싶은 경우
     *  -> 삭제되었다는 표현을 통해 기본 이미지로 다시 업데이트   -> 유저 이미지 삭제 api로 해결
     */

    /**
     * 비밀번호 수정
     * @param nickname
     * @param request
     * @param userDetails
     * @return
     */
    @PatchMapping("/update-passwords/{nickname}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updatePassword(@PathVariable String nickname,
                               @RequestBody PasswordUpdateRequest request,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {

        userService.modifyPassword(nickname, request, userDetails.getUser());
    }

    /**
     * 비밀번호 찾기를 통한 비밀번호 재설정
     * @param nickname
     * @param request
     * @param userDetails
     * @return
     */
    @PatchMapping("/reissue-passwords/{nickname}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updatePassword(@PathVariable String nickname,
                               @RequestBody ReissuePasswordRequest request,
                               @AuthenticationPrincipal UserDetailsImpl userDetails) {

        userService.reissuePassword(nickname, request, userDetails.getUser());
    }
}
