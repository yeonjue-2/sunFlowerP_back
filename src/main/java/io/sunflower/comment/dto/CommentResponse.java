package io.sunflower.comment.dto;

import io.sunflower.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private String commentContent;
    private String nickname;
    private String userImageUrl;
    private LocalDateTime createAt;

    public CommentResponse(Comment comment) {
        this.commentContent = comment.getCommentContents();
        this.nickname = comment.getUser().getNickname();
        this.userImageUrl = comment.getUser().getUserImageUrl();
        this.createAt = comment.getCreatedAt();
    }

}
