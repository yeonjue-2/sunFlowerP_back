package io.sunflower.post.controller;

import io.sunflower.post.dto.PostRequest;
import io.sunflower.post.dto.PostResponse;
import io.sunflower.s3.S3Uploader;
import io.sunflower.security.UserDetailsImpl;
import io.sunflower.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final S3Uploader s3Uploader;

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    public PostResponse createPost(@RequestPart(value = "files") List<MultipartFile> files,
                                   @Valid @RequestPart(value = "dto") PostRequest request,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {

        s3Uploader.checkFileUpload(files);
        List<String> urls = s3Uploader.uploadFiles(files, "postImage");
        return postService.savePost(urls, request, userDetails.getUser());
    }

    // 포스트 단건 조회
    @GetMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse readPost(@PathVariable Long postId) {
        return postService.findPost(postId);
    }

    // 포스트 전체 조회
    @GetMapping("/posts/")
    @ResponseStatus(HttpStatus.OK)
    public List<PostResponse> readPosts() {
        return postService.findPosts();
    }

    @PatchMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostResponse updatePost(@PathVariable Long postId, @RequestBody PostRequest request,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.modifyPost(postId, request, userDetails.getUser());
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable Long postId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String curUserImage = userDetails.getUser().getUserImageUrl();
        s3Uploader.deleteFile(curUserImage, "userImage");

        postService.removePost(postId, userDetails.getUser());
    }
}
