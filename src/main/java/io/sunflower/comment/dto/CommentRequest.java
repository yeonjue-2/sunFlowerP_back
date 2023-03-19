package io.sunflower.comment.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class CommentRequest {

    @Length(max = 100, message = "댓글은 최대 100자까지 작성하실 수 있습니다.")
    private String commentContent;
}
