package io.sunflower.profile.controller;

import io.sunflower.profile.dto.ProfileUpdateRequest;
import io.sunflower.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService profileService;

//    @PutMapping("/")
//    public ResponseEntity<String> updateProfile(@RequestBody ProfileUpdateRequest request) {
//
//    }
}
