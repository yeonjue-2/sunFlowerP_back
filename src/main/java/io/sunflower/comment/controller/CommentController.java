package io.sunflower.comment.controller;

import io.sunflower.comment.dto.CommentRequest;
import io.sunflower.comment.dto.CommentResponse;
import io.sunflower.comment.service.CommentService;
import io.sunflower.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comment/")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse createComment(@PathVariable Long postId, @RequestBody CommentRequest request,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.saveComment(postId, request, userDetails.getUser());
    }

    /**
     * 포스트 조회시 내가 작성한 댓글이 있으면 먼저 조회
     */
    @GetMapping("{postId}/comment-read")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse readCommentByUser(@PathVariable Long postId,
                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.findCommentByUser(postId, userDetails.getUser());
    }

    /**
     * 포스트 조회 시 전체 댓글 조회
     */
    @GetMapping("/{postId}/comment/")
    @ResponseStatus(HttpStatus.OK)
    public Slice<CommentResponse> readComments(@PathVariable Long postId,
                                               @RequestParam("page") int page) {
        return commentService.findComments(postId, page);

    }

    @PatchMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponse updateComment(@PathVariable Long commentId, @RequestBody CommentRequest request,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.modifyComment(commentId, request, userDetails.getUser());
    }

    @DeleteMapping("/comment/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteComment(@PathVariable Long commentId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentService.removeComment(commentId, userDetails.getUser());
    }
}
