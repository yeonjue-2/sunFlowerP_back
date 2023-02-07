package io.sunflower.post.controller;

import io.sunflower.post.dto.PostRequest;
import io.sunflower.post.dto.PostUpdateRequest;
import io.sunflower.post.dto.PostResponse;
import io.sunflower.security.UserDetailsImpl;
import io.sunflower.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    @PreAuthorize("hasRole('ROLE_USER')")
    public PostResponse createPost(@RequestBody PostRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return postService.uploadPost(request, userDetails.getUser());
    }


    // 포스트 전체 조회
    @GetMapping("/posts/")
    public List<PostResponse> getPosts() {
        return postService.findPosts();
    }

    // 포스트 단건 조회
    @GetMapping("/posts/{postId}")
    public PostResponse getPost(@PathVariable Long postId) {
        return postService.findPost(postId);
    }


    @PutMapping("/posts/{postId}")
    public PostResponse updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest request,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.modifyPost(postId, request, userDetails.getUser());
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.removePost(postId, userDetails.getUser());
        return new ResponseEntity<>("delete success", HttpStatus.OK);
    }
}