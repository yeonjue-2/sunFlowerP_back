package io.sunflower.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenRequest {

    private String accessToken;
    private String refreshToken;

    public boolean validateToken(String validRefreshToken) {
        return this.refreshToken.equals(validRefreshToken);
    }
}
