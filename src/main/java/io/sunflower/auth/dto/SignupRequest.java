package io.sunflower.auth.dto;


import lombok.Getter;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
public class SignupRequest {

    @NotEmpty(message = "이메일 주소는 필수입니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
//    @Pattern(regexp = "^[a-zA-Z0-9_+.-]+@([a-z0-9-]+\\.)+[a-z0-9]{2,4}$")
    private String emailId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    @Length(min = 8, max = 14, message = "비밀번호는 8 ~ 14자로 설정해 주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*])[A-Za-z\\d~!@#$%^&*]{8,14}")
    private String password;

    @NotEmpty(message = "닉네임은 필수입니다.")
    @Length(min = 2, max = 15, message = "닉네임은 2 ~ 15자로 설정해 주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9-_]*$")
    private String nickname;

    private boolean admin = false;
    private String adminToken = "";
}
