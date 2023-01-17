package io.sunflower.dto.request;

import io.sunflower.entity.enumeration.UserGenderEnum;
import lombok.Getter;

@Getter
public class SignupRequest {
    private String personalId;
    private String password;
    private String nickname;
    private String email;
    private UserGenderEnum gender;
    private String userContents;
    private String kakaoId = "";
    private boolean admin = false;
    private String adminToken = "";

//    private String userImage;

    private boolean isMember = true;
//    private boolean isValidMail = true;
}
