package io.sunflower.dto.request;

import io.sunflower.entity.enumeration.UserGenderEnum;
import lombok.Getter;

@Getter
public class UserRequest {

    private String userPersonalId;
    private String userPassword;
    private String userNickname;
    private String userEmail;
    private UserGenderEnum userGender;
    private String userContents;
    private String userKaKaoId = "";

//    private String userImage;

    private boolean isMember = true;
//    private boolean isValidMail = true;

}
