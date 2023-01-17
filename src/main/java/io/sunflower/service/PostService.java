package io.sunflower.service;

import io.sunflower.dto.request.PostRequest;
import io.sunflower.dto.response.PostResponse;
import io.sunflower.entity.Post;
import io.sunflower.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public PostResponse makePost(PostRequest request) {
        Post post = new Post(request);
        postRepository.save(post);

        return new PostResponse(post);
    }
}
