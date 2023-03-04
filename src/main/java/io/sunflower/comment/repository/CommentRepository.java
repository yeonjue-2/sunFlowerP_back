package io.sunflower.comment.repository;

import io.sunflower.comment.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Slice<Comment> findAllByPostIdOrderByCreatedAtDesc(Long postId,Pageable pageable);
}
