package io.sunflower.post.service;

import io.sunflower.post.dto.PostRequest;
import io.sunflower.post.dto.PostUpdateRequest;
import io.sunflower.post.dto.PostResponse;
import io.sunflower.post.entity.Post;
import io.sunflower.user.entity.User;
import io.sunflower.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static io.sunflower.common.exception.model.PostException.PostNotFoundException;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public PostResponse uploadPost(PostRequest request, User user) {

        Post post = request.toEntity(user);
        postRepository.save(post);

        return new PostResponse(post, user);
    }

    public List<PostResponse> findPosts() {
        List<PostResponse> responses = new ArrayList<>();
        List<Post> posts = postRepository.findAll();

        for (Post post : posts) {
            PostResponse response = new PostResponse(post);
            responses.add(response);
        }

        return responses;
    }

    public PostResponse findPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        PostResponse response = new PostResponse(post);

        return response;
    }

    @Transactional
    public PostResponse modifyPost(Long postId, PostUpdateRequest request, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        List<PostUpdateRequest> postUpdateRequests = new ArrayList<>();
        postUpdateRequests.add(request);

        if (post.getUser().getEmailId().equals(user.getEmailId())) {
            post.update(postUpdateRequests);
            postRepository.save(post);
            return new PostResponse(post, user);
        } else {
            throw new IllegalArgumentException("접근할 수 있는 권한이 없습니다.");
        }

    }

    @Transactional
    public void removePost(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if (post.getUser().getEmailId().equals(user.getEmailId())) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("접근할 수 있는 권한이 없습니다.");
        }
    }
}
