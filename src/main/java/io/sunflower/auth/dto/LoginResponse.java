package io.sunflower.auth.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class LoginResponse {

    private String emailId;
    private String accessToken;
    private String refreshToken;

    public static LoginResponse of(String emailId, String accessToken, String refreshToken) {
        return LoginResponse.builder()
                .emailId(emailId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
