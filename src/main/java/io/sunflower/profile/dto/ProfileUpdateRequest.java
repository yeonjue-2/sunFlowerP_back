package io.sunflower.profile.dto;

import io.sunflower.entity.enumeration.UserGenderEnum;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class ProfileUpdateRequest {

    @NotEmpty(message = "닉네임은 필수입니다.")
    @Length(min = 2, max = 12, message = "닉네임은 2 ~ 12자로 설정해 주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9-_]*$", message = "한글")
    private String nickname;

    @Length(max = 100, message = "소개글은 최대 100자까지 작성하실 수 있습니다.")
    private String userContents;

    private UserGenderEnum gender;
//    private String userImage;
//    private boolean isActivated = true;

}
