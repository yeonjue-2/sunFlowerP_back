package io.sunflower.post.controller;

import io.sunflower.post.dto.PostResponse;
import io.sunflower.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/search-posts")
public class PostSearchController {

    private final PostService postService;

    /**
     * 포스트 검색 조회
     * 댓글 많은 순 정렬, 하트 많은 순 정렬
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Slice<PostResponse> searchPosts(@PageableDefault(size = 15) Pageable pageable,
                                           @RequestParam(required = false, name = "sort") String likeCount,
                                           @RequestParam String keyword) {
        return postService.searchPosts(pageable, keyword, likeCount);
    }

}
