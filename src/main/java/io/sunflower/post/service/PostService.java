package io.sunflower.post.service;

import io.sunflower.common.exception.model.InvalidAccessException;
import io.sunflower.common.exception.model.NotFoundException;
import io.sunflower.post.dto.PostRequest;
import io.sunflower.post.dto.PostResponse;
import io.sunflower.post.entity.Post;
import io.sunflower.post.entity.PostImage;
import io.sunflower.user.entity.User;
import io.sunflower.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static io.sunflower.common.exception.ExceptionStatus.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    public PostResponse findPost(Long postId) {
        Post post = getPostEntity(postId);
        return new PostResponse(post);
    }

    public Slice<PostResponse> findPosts(Pageable pageable) {
        Slice<Post> posts = postRepository.findAllByOrderByCreatedAtDesc(pageable);

        return posts.map(PostResponse::new);
    }

    public Slice<PostResponse> searchPosts(String keyword, Pageable pageable) {
//        Slice<Post> posts = postRepository.findByPostContentsContaining(keyword, pageable);
        Slice<Post> posts = postRepository.findByMenuListContainingOrderByCreatedAtDesc(keyword, pageable);

        return posts.map(PostResponse::new);
    }

    @Transactional
    public PostResponse savePost(List<String> urls, PostRequest request, User user) {

        Post post = request.toEntity(user);

        saveImages(urls, post);
        postRepository.save(post);

        return new PostResponse(post, user);
    }

    @Transactional
    public PostResponse modifyPost(Long postId, PostRequest request, User user) {
        Post post = getPostEntity(postId);

        if (post.getUser().getEmailId().equals(user.getEmailId())) {
            post.update(request);
            postRepository.save(post);
            return new PostResponse(post, user);
        } else {
            throw new InvalidAccessException(NOT_AUTHORIZED_POST);
        }

    }

    @Transactional
    public void removePost(Long postId, User user) {
        Post post = getPostEntity(postId);

        if (post.getUser().getEmailId().equals(user.getEmailId())) {
            postRepository.delete(post);
        } else {
            throw new InvalidAccessException(NOT_AUTHORIZED_POST);
        }
    }


    // ==================== 내부 메서드 ======================

    /**
     * Id를 이용해 Post 객체 찾기
     * @param postId
     */
    private Post getPostEntity(long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(NOT_FOUND_POST)
        );
    }

    /**
     * 이미지 저장
     *
     * @param urls
     * @param post
     */
    @Transactional
    public void saveImages(List<String> urls, Post post) {
        for (String url : urls) {
            PostImage postImage = new PostImage(url, post);
            post.addPostImage(postImage);
        }
    }


}
