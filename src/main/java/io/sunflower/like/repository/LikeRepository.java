package io.sunflower.like.repository;

import io.sunflower.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    int countByPostId(Long postId);

    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);
}
