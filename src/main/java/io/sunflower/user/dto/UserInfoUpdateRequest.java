package io.sunflower.user.dto;

import io.sunflower.common.enumeration.UserGenderEnum;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static io.sunflower.common.constant.UserConst.NECESSARY_PASSWORD;
import static io.sunflower.common.constant.UserConst.PASSWORD_CONDITION;

@Getter
public class UserInfoUpdateRequest {

    @NotEmpty(message = "닉네임은 필수입니다.")
    @Length(min = 2, max = 12, message = "닉네임은 2 ~ 12자로 설정해 주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9-_]*$", message = "한글")
    private String nickname;

    @Length(max = 100, message = "소개글은 최대 100자까지 작성하실 수 있습니다.")
    private String userContents;

    private UserGenderEnum gender;
    private MultipartFile userImage;
}
