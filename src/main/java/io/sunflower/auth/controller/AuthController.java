package io.sunflower.auth.controller;

import io.sunflower.auth.dto.LoginResponse;
import io.sunflower.auth.dto.ReissueResponse;
import io.sunflower.kakao.KakaoService;
import io.sunflower.auth.dto.LoginRequest;
import io.sunflower.auth.dto.SignupRequest;
import io.sunflower.auth.service.AuthService;
import io.sunflower.s3.S3Uploader;
import io.sunflower.security.jwt.dto.TokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final KakaoService kakaoService;
    private final S3Uploader s3Uploader;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Valid SignupRequest request) {

//        String userImageUrl = "6b6528a3-2396-4126-acfa-8e8f06397a57.png";
        String userImageUrl;

        if (request.getUserImage() != null) {
            s3Uploader.checkFileExtension(request.getUserImage().getOriginalFilename());
            userImageUrl = s3Uploader.uploadFile(request.getUserImage(), "postImage");
        } else {
            userImageUrl = "6b6528a3-2396-4126-acfa-8e8f06397a57.png";
        }

        authService.signup(userImageUrl, request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResponse login(@RequestBody LoginRequest request, HttpServletResponse response) {
        return authService.login(request, response);
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public ReissueResponse reissue(@RequestBody TokenRequest request, HttpServletResponse response) {
        return authService.reissue(request, response);
    }

    @DeleteMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void signOut(final @RequestBody TokenRequest request) {
        authService.logout(request);
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