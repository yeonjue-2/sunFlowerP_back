package io.sunflower.user.controller;

import io.sunflower.auth.service.AuthService;
import io.sunflower.security.UserDetailsImpl;
import io.sunflower.user.dto.UserInfoUpdateRequest;
import io.sunflower.user.entity.User;
import io.sunflower.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {
    private final UserRepository userRepository;
    private final AuthService authService;

    @GetMapping("/passwords")
    public ResponseEntity<Boolean> checkPassword(@RequestBody UserInfoUpdateRequest request,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        authService.checkPassword(request.getPassword(), userDetails.getUser());
        return ResponseEntity.ok(true);


    }
}
