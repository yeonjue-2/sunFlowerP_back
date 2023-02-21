package io.sunflower.auth.controller;

import io.sunflower.auth.dto.LoginResponse;
import io.sunflower.auth.dto.ReissueResponse;
import io.sunflower.kakao.KakaoService;
import io.sunflower.auth.dto.LoginRequest;
import io.sunflower.auth.dto.SignupRequest;
import io.sunflower.auth.service.AuthService;
import io.sunflower.security.jwt.dto.TokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final KakaoService kakaoService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Valid SignupRequest request) {
        authService.signup(request);
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
//    @GetMapping("/kakao/callback")
//    @ResponseStatus(HttpStatus.OK)
//    public HttpServletResponse kakaoLogin(@RequestParam String code, HttpServletResponse response) throws IOException {
//
//        String createToken = kakaoService.kakaoLogin(code, response);
//
//        // Cookie 생성 및 직접 브라우저에 Set
//        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, createToken.substring(7));   // 키 , 밸류
//        cookie.setPath("/");  // 해당 url로 쿠키 전송
//        response.addCookie(cookie);
//
//        return response;
//    }

    @GetMapping("/kakao/callback")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse kakaoLogin(@RequestParam String code) throws IOException {

        return kakaoService.kakaoLogin(code);
    }

}