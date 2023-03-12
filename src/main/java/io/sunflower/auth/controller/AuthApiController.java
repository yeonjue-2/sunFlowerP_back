package io.sunflower.auth.controller;

import io.sunflower.auth.dto.PasswordRequest;
import io.sunflower.auth.service.AuthService;
import io.sunflower.security.UserDetailsImpl;
import io.sunflower.user.dto.PasswordUpdateRequest;
import io.sunflower.user.dto.UserInfoUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApiController {

    private final AuthService authService;

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
}
