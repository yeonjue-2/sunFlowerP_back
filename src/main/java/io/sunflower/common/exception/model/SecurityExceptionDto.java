package io.sunflower.common.exception.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SecurityExceptionDto extends RuntimeException{

    private int statusCode;
    private String message;

    public SecurityExceptionDto(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
