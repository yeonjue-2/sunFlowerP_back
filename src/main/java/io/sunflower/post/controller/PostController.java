package io.sunflower.post.controller;

import io.sunflower.post.dto.PostRequest;
import io.sunflower.post.dto.PostDetailResponse;
import io.sunflower.s3.S3Uploader;
import io.sunflower.security.UserDetailsImpl;
import io.sunflower.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final S3Uploader s3Uploader;

    @PostMapping(value = "/", consumes = {"multipart/form-data"})
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_USER')")
    public PostDetailResponse createPost(PostRequest request,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {

        s3Uploader.checkFileUpload(request.getFiles());
        List<String> urls = s3Uploader.uploadFiles(request.getFiles(), "postImage");
        return postService.savePost(urls, request, userDetails.getUser());
    }


    // 포스트 단건 조회 및 전체 댓글 조회
    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostDetailResponse readPost(@PathVariable Long postId) {
        return postService.findPost(postId);
    }


    /**
     * 포스트 전체 조회(메인 화면, 최신순 정렬(createdAt = Desc))
     * 댓글 많은 순 정렬, 하트 많은 순 정렬
     */
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Slice<PostDetailResponse> readPosts(@PageableDefault(size = 15) Pageable pageable) {
        return postService.findPosts(pageable);
    }

    /**
     * 포스트 검색 조회
     * 댓글 많은 순 정렬, 하트 많은 순 정렬
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Slice<PostDetailResponse> searchPosts(@PageableDefault(size = 15) Pageable pageable,
                                                 @RequestParam String keyword) {
        return postService.searchPosts(keyword, pageable);
    }

    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public PostDetailResponse updatePost(@PathVariable Long postId, @RequestBody PostRequest request,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.modifyPost(postId, request, userDetails.getUser());
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePost(@PathVariable Long postId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String curUserImage = userDetails.getUser().getUserImageUrl();
        s3Uploader.deleteFile(curUserImage, "userImage");

        postService.removePost(postId, userDetails.getUser());
    }
}
