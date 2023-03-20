package io.sunflower.auth.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static io.sunflower.common.constant.UserConst.*;

@Getter
public class PasswordRequest {

    @NotEmpty(message = NECESSARY_PASSWORD)
    @Length(min = 8, max = 14, message = PASSWORD_LENGTH_CONDITION)
    @Pattern(regexp = PASSWORD_REGEXP, message = PASSWORD_CONDITION)
    private String password;
}
