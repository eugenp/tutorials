package com.baeldung.httpstatuscode.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleGoneException(CustomException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }
}
