package io.sunflower.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.sunflower.kakao.dto.KakaoService;
import io.sunflower.kakao.dto.KakaoUserAddInfoRequest;
import io.sunflower.user.dto.LoginRequest;
import io.sunflower.user.dto.SignupRequest;
import io.sunflower.security.jwt.JwtUtil;
import io.sunflower.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;

//    @GetMapping("/signup")
//    public ModelAndView signupPage() {
//        return new ModelAndView("signup");
//    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequest request) {
        userService.signup(request);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        String token = userService.login(request);
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);      // 헤더에 넣는 작업을 controller에서 처리

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    /**
     * 카카오에서 주는 인가코드를 받아(controller), 로그인 처리(service)
     * @param code: 카카오 서버로부터 받은 인가 코드
     * @return redirect url
     */
    @GetMapping("/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {

        String createToken = kakaoService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 Set
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));   // 키 , 밸류
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/api/user/login";
    }

//    // 카카오 가입 뒤 추가 정보 입력
//    @PostMapping("/kakao/addinfo")
//    public ResponseEntity<String> addInfo(@RequestBody @Valid KakaoUserAddInfoRequest request) {
//
//    }
}