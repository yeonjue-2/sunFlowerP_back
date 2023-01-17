package io.sunflower.controller;

import io.sunflower.dto.request.PostRequest;
import io.sunflower.dto.response.PostResponse;
import io.sunflower.entity.Post;
import io.sunflower.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public PostResponse createPost(@RequestBody PostRequest request) {
        return postService.makePost(request);
    }
}
