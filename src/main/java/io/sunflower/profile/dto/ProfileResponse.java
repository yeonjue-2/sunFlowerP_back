package io.sunflower.profile.dto;

import io.sunflower.entity.enumeration.UserGenderEnum;
import io.sunflower.user.entity.User;
import lombok.Getter;

@Getter
public class ProfileResponse {

    private String emailId;
    private String nickname;
    private String userContents;
    private UserGenderEnum gender;

    public ProfileResponse(User user) {
        this.emailId = user.getEmailId();
        this.nickname = user.getNickname();
        this.userContents = user.getUserContents();
        this.gender = user.getGender();
    }

}
