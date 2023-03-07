package io.sunflower.like.controller;

import io.sunflower.like.service.LikeService;
import io.sunflower.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}/like")
    @ResponseStatus(HttpStatus.CREATED)
    public void createLike(@PathVariable Long postId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.saveLike(postId, userDetails.getUser());
    }

    @DeleteMapping("/{postId}/like")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLike(@PathVariable Long postId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likeService.removeLike(postId, userDetails.getUser());
    }
}
