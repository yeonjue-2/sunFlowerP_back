package io.sunflower.auth.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class LoginResponse {

    private String nickname;
    private String accessToken;
    private String refreshToken;

    public static LoginResponse of(String nickname, String accessToken, String refreshToken) {
        return LoginResponse.builder()
                .nickname(nickname)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
