package io.sunflower.user.dto;

import io.sunflower.common.enumeration.UserGenderEnum;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static io.sunflower.common.constant.UserMessageConst.NECESSARY_PASSWORD;
import static io.sunflower.common.constant.UserMessageConst.PASSWORD_CONDITION;

@Getter
public class UserInfoUpdateRequest {

    @NotEmpty(message = NECESSARY_PASSWORD)
    @Length(min = 8, max = 14, message = PASSWORD_CONDITION)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*])[A-Za-z\\d~!@#$%^&*]{8,14}", message = "")
    private String password;

    @NotEmpty(message = "닉네임은 필수입니다.")
    @Length(min = 2, max = 12, message = "닉네임은 2 ~ 12자로 설정해 주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9-_]*$", message = "한글")
    private String nickname;

    @Length(max = 100, message = "소개글은 최대 100자까지 작성하실 수 있습니다.")
    private String userContents;

    private UserGenderEnum gender;
    // TO-DO
//    private String userImage;
}
