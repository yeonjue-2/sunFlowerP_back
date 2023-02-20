package io.sunflower.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class LoginResponse {

    private String emailId;
    private String accessToken;
    private String refreshToken;

    public static LoginResponse of(String emailId, String accessToken, String refreshToken) {
        return new LoginResponse(emailId, accessToken, refreshToken);
    }
}
