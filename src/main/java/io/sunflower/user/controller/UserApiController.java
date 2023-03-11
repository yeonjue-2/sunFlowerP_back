package io.sunflower.user.controller;


import io.sunflower.user.dto.UserModalInfoResponse;
import io.sunflower.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {
    private final UserService userService;

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
}
