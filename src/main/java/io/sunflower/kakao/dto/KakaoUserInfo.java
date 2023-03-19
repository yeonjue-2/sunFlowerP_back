package io.sunflower.kakao.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfo {
    private Long id;
    private String kakaoEmail;
    private String kakaoNickname;

    public KakaoUserInfo(Long id, String kakaoEmail, String kakaoNickname) {
        this.id = id;
        this.kakaoEmail = kakaoEmail;
        this.kakaoNickname = kakaoNickname;
    }
}
