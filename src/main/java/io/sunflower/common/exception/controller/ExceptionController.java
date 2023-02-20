package io.sunflower.common.exception.controller;

import io.sunflower.common.exception.model.AuthException;
import io.sunflower.common.exception.dto.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({AuthException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse authException(AuthException ex) {
        log.error(ex.getMessage());
        return new ExceptionResponse(ex.getCode(), ex.getMessage());
    }
}
