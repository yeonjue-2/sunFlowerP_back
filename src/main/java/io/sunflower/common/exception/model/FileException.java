package io.sunflower.common.exception.model;

import io.sunflower.common.exception.ExceptionStatus;
import lombok.Getter;

@Getter
public class FileException extends RuntimeException {

    private final int code;
    private final String message;

    public FileException(ExceptionStatus status) {
        this.code = status.getStatusCode();
        this.message = status.getMessage();
    }

}
