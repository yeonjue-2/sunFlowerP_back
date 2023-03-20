package io.sunflower.user.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static io.sunflower.common.constant.UserConst.*;
import static io.sunflower.common.constant.UserConst.PASSWORD_CONDITION;

@Getter
public class ReissuePasswordRequest {
    @NotEmpty(message = NECESSARY_PASSWORD)
    @Length(min = 8, max = 14, message = PASSWORD_LENGTH_CONDITION)
    @Pattern(regexp = PASSWORD_REGEXP, message = PASSWORD_CONDITION)
    private String password;
}
