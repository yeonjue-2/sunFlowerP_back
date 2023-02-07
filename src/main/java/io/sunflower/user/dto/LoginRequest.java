package io.sunflower.user.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String emailId;
    private String password;
}
