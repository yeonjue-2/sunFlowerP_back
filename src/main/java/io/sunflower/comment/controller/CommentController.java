package io.sunflower.comment.controller;

import io.sunflower.comment.dto.CommentRequest;
import io.sunflower.comment.dto.CommentResponse;
import io.sunflower.comment.service.CommentService;
import io.sunflower.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse createComment(@PathVariable Long postId, @RequestBody CommentRequest request,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.saveComment(postId, request, userDetails.getUser());
    }
}
