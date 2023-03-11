package io.sunflower.comment.repository;

import io.sunflower.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByPostIdAndUserId(Long postId, Long userId);
    Slice<Comment> findAllByPostIdOrderByCreatedAtDesc(Long postId,Pageable pageable);
}
