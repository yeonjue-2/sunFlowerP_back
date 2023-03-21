package io.sunflower.user.controller;


import io.sunflower.s3.S3Uploader;
import io.sunflower.security.UserDetailsImpl;
import io.sunflower.user.dto.UserModalInfoResponse;
import io.sunflower.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static io.sunflower.common.constant.UserConst.DEFAULT_USER_IMAGE;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;
    private final S3Uploader s3Uploader;

    /**
     * 호버링 시 nickname 유저의 프로필을 간략하게 모달로 보기
     *
     * @param nickname
     * @return
     */
    @GetMapping("/{nickname}")
    @ResponseStatus(HttpStatus.OK)
    public UserModalInfoResponse readUserInModal(@PathVariable String nickname) {
        return userService.findUserInModal(nickname);
    }

    /**
     * 유저 정보 수정 시 유저의 설정된 이미지 삭제 -> 최종 수정 저장 시 기본이미지로 저장
     */
    @DeleteMapping("/{nickname}/delete-image")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Boolean> deleteUserImage(@PathVariable String nickname, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        s3Uploader.deleteFile(userDetails.getUser().getUserImageUrl(), "userImage");  // 유저의 이미지 삭제

        return ResponseEntity.ok(userService.removeUserImage(nickname, DEFAULT_USER_IMAGE, userDetails.getUser()));
    }

}
