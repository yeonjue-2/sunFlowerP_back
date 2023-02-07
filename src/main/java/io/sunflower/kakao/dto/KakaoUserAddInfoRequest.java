package io.sunflower.kakao.dto;

import io.sunflower.entity.enumeration.UserGenderEnum;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class KakaoUserAddInfoRequest {

    private UserGenderEnum gender;

    @Length(max = 100, message = "소개글은 최대 100자까지 작성하실 수 있습니다.")
    private String userContents;
    private boolean admin = false;
    private String adminToken = "";

//    private String userImage;

    private boolean isMember = true;
//    private boolean isActivated = true;
}
