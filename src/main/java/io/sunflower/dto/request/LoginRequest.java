package io.sunflower.dto.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String personalId;
    private String password;
}
