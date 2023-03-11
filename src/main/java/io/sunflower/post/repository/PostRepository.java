package io.sunflower.post.repository;

import io.sunflower.post.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserId(Long userId);

    Slice<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Slice<Post> findByMenuListContainingOrderByCreatedAtDesc(String keyword, Pageable pageable);
    Slice<Post> findByMenuListContaining(String keyword, Pageable pageable);


}
