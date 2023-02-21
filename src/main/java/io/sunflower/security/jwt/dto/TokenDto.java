package io.sunflower.security.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

    @Builder
    public TokenDto(String grantType, String accessToken, String refreshToken,
                    Long accessTokenExpiresIn) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }
}
