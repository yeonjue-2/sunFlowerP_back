package io.sunflower.controller;

import io.sunflower.dto.request.LoginRequest;
import io.sunflower.dto.request.SignupRequest;
import io.sunflower.jwt.JwtUtil;
import io.sunflower.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

//    @GetMapping("/signup")
//    public ModelAndView signupPage() {
//        return new ModelAndView("signup");
//    }
//
//    @GetMapping("/login-page")
//    public ModelAndView loginPage() {
//        return new ModelAndView("login");
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
}