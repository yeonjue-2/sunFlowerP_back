package io.sunflower.post.dto;

import io.sunflower.post.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class PostResponse {
    private Long postId;
    private String postImageUrl;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createAt;

    public PostResponse(Post post) {
        this.postId = post.getId();
        this.postImageUrl = post.getPostImages().get(0).getPostImageUrl();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getComments().size();
        this.createAt = post.getCreatedAt();
    }
}
