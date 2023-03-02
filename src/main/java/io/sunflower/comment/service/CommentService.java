package io.sunflower.comment.service;

import io.sunflower.comment.dto.CommentRequest;
import io.sunflower.comment.dto.CommentResponse;
import io.sunflower.comment.entity.Comment;
import io.sunflower.comment.repository.CommentRepository;
import io.sunflower.post.entity.Post;
import io.sunflower.post.service.PostService;
import io.sunflower.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    @Transactional
    public CommentResponse saveComment(Long postId, CommentRequest request, User user) {
        Post post = postService.getPostEntity(postId);
        Comment comment = new Comment(request, post, user);

        post.addComment(comment);
        comment.addPost(post);

        commentRepository.save(comment);

        return new CommentResponse(comment, user);
    }
}
