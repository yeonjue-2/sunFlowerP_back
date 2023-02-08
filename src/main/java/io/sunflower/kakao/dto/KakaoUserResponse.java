package io.sunflower.kakao.dto;

import io.sunflower.entity.enumeration.UserGenderEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
public class KakaoUserResponse {
    private String emailId;
    private String password;
    private String nickname;
    private String userContents;
    private UserGenderEnum gender;

    @Builder
    public KakaoUserResponse(String emailId, String password, String nickname, String userContents, UserGenderEnum gender) {
        this.emailId = emailId;
        this.password = password;
        this.nickname = nickname;
        this.userContents = userContents;
        this.gender = gender;
    }
}
