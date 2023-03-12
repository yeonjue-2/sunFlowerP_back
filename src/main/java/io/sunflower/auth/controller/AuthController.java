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

import static io.sunflower.common.constant.UserConst.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final S3Uploader s3Uploader;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody @Valid SignupRequest request) {

//        String userImageUrl = DEFAULT_USER_IMAGE;
//
//        if (request.getUserImage() != null) {
//            s3Uploader.checkFileExtension(request.getUserImage().getOriginalFilename());
//            userImageUrl = s3Uploader.uploadFile(request.getUserImage(), "userImage");
//        }

        authService.signup(DEFAULT_USER_IMAGE, request);
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
    public void logOut(final @RequestBody TokenRequest request) {
        authService.logout(request);
    }

}