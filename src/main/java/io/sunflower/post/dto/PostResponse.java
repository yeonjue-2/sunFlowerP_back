package io.sunflower.post.dto;

import io.sunflower.common.enumeration.MealCountEnum;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostResponse {
    private Long postId;
    private List<String> postImageUrls;
    private LocalDateTime createAt;
}
