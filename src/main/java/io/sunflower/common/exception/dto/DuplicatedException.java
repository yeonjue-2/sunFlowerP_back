package io.sunflower.common.exception.dto;

import io.sunflower.common.exception.ExceptionStatus;
import lombok.Getter;

@Getter
public class DuplicatedException extends RuntimeException {

    private final int code;
    private final String message;

    public DuplicatedException(ExceptionStatus status) {
        this.code = status.getStatusCode();
        this.message = status.getMessage();
    }

}
