package io.sunflower.service;

import io.sunflower.dto.request.PostRequest;
import io.sunflower.dto.response.PostResponse;
import io.sunflower.entity.Post;
import io.sunflower.entity.User;
import io.sunflower.repository.PostRepository;
import io.sunflower.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public PostResponse makePost(PostRequest request, User user) {
        System.out.println("ProductService.createProduct");
        System.out.println("user.getUsername() = " + user.getPersonalId());

        Post post = postRepository.save(new Post(request, user));

        return new PostResponse(post);
    }
}
