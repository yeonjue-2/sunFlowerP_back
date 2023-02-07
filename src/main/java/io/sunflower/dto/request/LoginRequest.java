package io.sunflower.dto.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String emailId;
    private String password;
}
