package io.sunflower.like.service;

import io.sunflower.common.exception.model.DuplicatedException;
import io.sunflower.common.exception.model.NotFoundException;
import io.sunflower.like.entity.Like;
import io.sunflower.like.repository.LikeRepository;
import io.sunflower.post.entity.Post;
import io.sunflower.post.repository.PostRepository;
import io.sunflower.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.sunflower.common.exception.ExceptionStatus.*;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public void saveLike(Long postId, User user) {
        Post post = getPostEntity(postId);

        validateLike(postId, user);

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        likeRepository.save(like);
    }

    public void removeLike(Long postId, User user) {
        Like like = likeRepository.findByPostIdAndUserId(postId, user.getId()).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_LIKE)
        );

        likeRepository.delete(like);
    }

    public Long findLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }



    // ==================== 내부 메서드 ======================

    /**
     * Id를 이용해 Post 객체 찾기
     * @param postId
     */
    public Post getPostEntity(long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_POST)
        );
    }

    private void validateLike(Long postId, User user) {
        if (likeRepository.existsBypostIdAndUserId(postId, user.getId())) {
            throw new DuplicatedException(DUPLICATED_LIKE);
        }
    }
}
