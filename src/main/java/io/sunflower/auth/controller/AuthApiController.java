package io.sunflower.auth.controller;

import io.sunflower.auth.dto.LoginResponse;
import io.sunflower.auth.dto.PasswordRequest;
import io.sunflower.auth.service.AuthService;
import io.sunflower.kakao.KakaoService;
import io.sunflower.security.UserDetailsImpl;
import io.sunflower.user.dto.PasswordUpdateRequest;
import io.sunflower.user.dto.UserInfoUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApiController {

    private final AuthService authService;
    private final KakaoService kakaoService;

    @GetMapping("/emailIds/{emailId}/exists")
    public ResponseEntity<Boolean> checkEmailIdDuplicate(@PathVariable String emailId) {
        return ResponseEntity.ok(authService.checkEmailIdDuplicate(emailId));
    }

    @GetMapping("/nicknames/{nickname}/exists")
    public ResponseEntity<Boolean> checkNicknameDuplicate(@PathVariable String nickname) {
        return ResponseEntity.ok(authService.checkNicknameDuplicate(nickname));
    }

    @GetMapping("/passwords")
    public ResponseEntity<Boolean> checkPassword(@RequestBody PasswordRequest request,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        authService.checkPassword(request.getPassword(), userDetails.getUser());
        return ResponseEntity.ok(true);
    }

    /**
     * 카카오에서 주는 인가코드를 받아(controller), 로그인 처리(service)
     * @param code: 카카오 서버로부터 받은 인가 코드
     * @return response body에 정보 채워서 전달
     * https://kauth.kakao.com/oauth/authorize?client_id=86483f30e78c6016d89913f11cd358ce&
     * redirect_uri=http://localhost:8080/api/auth/kakao/callback&response_type=code
     */
    @GetMapping("/kakao/callback")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse kakaoLogin(@RequestParam String code) throws IOException {

        return kakaoService.kakaoLogin(code);
    }
}
