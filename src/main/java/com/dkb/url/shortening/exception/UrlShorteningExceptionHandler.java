package com.dkb.url.shortening.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UrlShorteningExceptionHandler{

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<String> deviceNotFoundException(UrlNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
