package io.sunflower.user.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static io.sunflower.common.constant.UserConst.NECESSARY_PASSWORD;
import static io.sunflower.common.constant.UserConst.PASSWORD_CONDITION;

@Getter
public class PasswordUpdateRequest {

    @NotEmpty(message = NECESSARY_PASSWORD)
    @Length(min = 8, max = 14, message = PASSWORD_CONDITION)
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*])[A-Za-z\\d~!@#$%^&*]{8,14}", message = "")
    private String password;
}
