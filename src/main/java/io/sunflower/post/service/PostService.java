package io.sunflower.post.service;

import io.sunflower.common.exception.model.InvalidAccessException;
import io.sunflower.common.exception.model.NotFoundException;
import io.sunflower.post.dto.PostRequest;
import io.sunflower.post.dto.PostDetailResponse;
import io.sunflower.post.dto.PostResponse;
import io.sunflower.post.entity.Post;
import io.sunflower.post.entity.PostImage;
import io.sunflower.user.entity.User;
import io.sunflower.post.repository.PostRepository;
import io.sunflower.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.sunflower.common.constant.PagingConstant.DEFAULT_PAGE_SIZE;
import static io.sunflower.common.exception.ExceptionStatus.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostDetailResponse findPost(Long postId) {
        Post post = getPostEntity(postId);
        return new PostDetailResponse(post);
    }

    public Slice<PostResponse> findPosts(int page, String likeCount) {

        Slice<Post> posts;
        Pageable pageable = PageRequest.of(page-1, DEFAULT_PAGE_SIZE);

        if (likeCount != null) {
            pageable = PageRequest.of(page-1, DEFAULT_PAGE_SIZE, Sort.by(Sort.Order.desc(likeCount), Sort.Order.desc("id")));
            posts = postRepository.findAll(pageable);

            return posts.map(PostResponse::new);
        }

        posts = postRepository.findAllByOrderByIdDesc(pageable);
        return posts.map(PostResponse::new);
    }

    public Slice<PostResponse> searchPosts(int page, String keyword, String likeCount) {
        Slice<Post> posts;
        Pageable pageable = PageRequest.of(page-1, DEFAULT_PAGE_SIZE);

        if (likeCount != null) {
            pageable = PageRequest.of(page-1, DEFAULT_PAGE_SIZE, Sort.by(Sort.Order.desc(likeCount), Sort.Order.desc("id")));
            posts = postRepository.findByMenuListContaining(keyword, pageable);

            return posts.map(PostResponse::new);
        }

        posts = postRepository.findByMenuListContainingOrderByIdDesc(keyword, pageable);
        return posts.map(PostResponse::new);
    }

    @Transactional
    public PostDetailResponse savePost(List<String> urls, PostRequest request, User user) {
        Post post = new Post(request, user);
        User userById = userService.findUserByEmailId(user.getEmailId());

        saveImages(urls, post);
        userById.addPost(post);
        postRepository.save(post);

        return new PostDetailResponse(post, userById);
    }

    @Transactional
    public PostDetailResponse modifyPost(Long postId, PostRequest request, User user) {
        Post post = getPostEntity(postId);

        if (post.getUser().getEmailId().equals(user.getEmailId())) {
            post.update(request);
            postRepository.save(post);
            return new PostDetailResponse(post, user);
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
    public Post getPostEntity(long postId) {
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
