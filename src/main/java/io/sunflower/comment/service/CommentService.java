package io.sunflower.comment.service;

import io.sunflower.comment.dto.CommentRequest;
import io.sunflower.comment.dto.CommentResponse;
import io.sunflower.comment.entity.Comment;
import io.sunflower.comment.repository.CommentRepository;
import io.sunflower.common.exception.model.InvalidAccessException;
import io.sunflower.common.exception.model.NotFoundException;
import io.sunflower.post.entity.Post;
import io.sunflower.post.service.PostService;
import io.sunflower.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.sunflower.common.constant.PagingConstant.DEFAULT_PAGE_SIZE;
import static io.sunflower.common.exception.ExceptionStatus.*;

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

        return CommentResponse.of(comment);
    }

    public CommentResponse findCommentByUser(Long postId, User user) {

        Comment comment = commentRepository.findByPostIdAndUserId(postId, user.getId()).orElseThrow(
                () -> new NotFoundException(NO_CONTENT_COMMENT)
        );

        return CommentResponse.of(comment);
    }

    public Slice<CommentResponse> findComments(Long postId, int page) {
        Pageable pageable = PageRequest.of(page-1, DEFAULT_PAGE_SIZE);
        Slice<Comment> comments = commentRepository.findAllByPostIdOrderByIdDesc(postId, pageable);

        return comments.map(CommentResponse::of);
    }

    @Transactional
    public CommentResponse modifyComment(Long commentId, CommentRequest request, User user) {
        Comment comment = getCommentEntity(commentId);

        if (comment.getUser().getEmailId().equals(user.getEmailId())) {
            comment.update(request);
            commentRepository.save(comment);
            return CommentResponse.of(comment);
        } else {
            throw new InvalidAccessException(NOT_AUTHORIZED_COMMENT);
        }

    }

    @Transactional
    public void removeComment(Long commentId, User user) {
        Comment comment = getCommentEntity(commentId);

        if (comment.getUser().getEmailId().equals(user.getEmailId())) {
            commentRepository.delete(comment);
        } else {
            throw new InvalidAccessException(NOT_AUTHORIZED_COMMENT);
        }
    }


    // ==================== 내부 메서드 ======================

    /**
     * Id를 이용해 Comment 객체 찾기
     * @param commentId
     */
    public Comment getCommentEntity(long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_COMMENT)
        );
    }
}
