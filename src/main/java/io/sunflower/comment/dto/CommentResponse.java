package io.sunflower.comment.dto;

import io.sunflower.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponse {

    private String commentContent;
    private String nickname;
    private String userImageUrl;
    private LocalDateTime createAt;

    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .commentContent(comment.getCommentContents())
                .nickname(comment.getUser().getNickname())
                .userImageUrl(comment.getUser().getUserImageUrl())
                .createAt(comment.getCreatedAt())
                .build();
    }
}
