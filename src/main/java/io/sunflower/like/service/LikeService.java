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

        // 좋아요 여부 판단
        validateLike(postId, user);

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        likeRepository.save(like);

        // 좋아요 저장 후 좋아요 수를 업데이트
        updateLikeCount(post);
    }

    public void removeLike(Long postId, User user) {
        Like like = likeRepository.findByPostIdAndUserId(postId, user.getId()).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_LIKE)
        );

        likeRepository.delete(like);
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
        if (likeRepository.existsByPostIdAndUserId(postId, user.getId())) {
            throw new DuplicatedException(DUPLICATED_LIKE);
        }
    }

    private void updateLikeCount(Post post) {
        int likeCount = likeRepository.countByPostId(post.getId());
        post.updateLike(likeCount);
        postRepository.save(post);
    }
}
