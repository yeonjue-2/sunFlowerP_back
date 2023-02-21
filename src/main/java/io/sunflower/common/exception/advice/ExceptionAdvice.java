package io.sunflower.common.exception.advice;

import io.sunflower.common.exception.model.*;
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
public class ExceptionAdvice {

    @ExceptionHandler({AuthException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse authException(AuthException ex) {
        log.error(ex.getMessage());
        return new ExceptionResponse(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler({DuplicatedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse duplicatedException(DuplicatedException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new ExceptionResponse(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({FileException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse fileException(FileException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new ExceptionResponse(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({InvalidAccessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse InvalidAccessException(InvalidAccessException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new ExceptionResponse(e.getCode(), e.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionResponse NotFoundException(NotFoundException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return new ExceptionResponse(e.getCode(), e.getMessage());
    }

// TO-DO
    // ========= 커스텀 익셉션 외 예외 처리 ==========

//    @ExceptionHandler({IOException.class})
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    protected ExceptionResponse IoStreamException(CustomException ex) {
//        ex.printStackTrace();
//        log.error(ex.getMessage());
//        return new ExceptionResponse(ex.getExceptionStatus().getStatusCode(),
//                ex.getExceptionStatus().getMessage());
//    }

}
