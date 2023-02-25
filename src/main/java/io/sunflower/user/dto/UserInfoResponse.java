package io.sunflower.user.dto;

import io.sunflower.common.enumeration.UserGenderEnum;
import io.sunflower.user.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponse {

    private String emailId;
    private String nickname;
    private String userContents;
    private UserGenderEnum gender;
    private String userImageUrl;

    public UserInfoResponse(User user) {
        this.emailId = user.getEmailId();
        this.nickname = user.getNickname();
        this.userContents = user.getUserContents();
        this.gender = user.getGender();
        this.userImageUrl = user.getUserImageUrl();
    }

}
