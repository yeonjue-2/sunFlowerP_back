package io.sunflower.comment.dto;

import io.sunflower.comment.entity.Comment;
import io.sunflower.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private String commentContent;
    private String nickname;
    private String userImageUrl;
    private LocalDateTime createAt;

    public CommentResponse(Comment comment, User user) {
        this.commentContent = comment.getCommentContents();
        this.nickname = user.getNickname();
        this.userImageUrl = user.getUserImageUrl();
        this.createAt = comment.getCreatedAt();
    }

}
