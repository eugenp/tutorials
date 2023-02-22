package com.baeldung.opentelemetry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PriceControllerAdvice {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<Object> handlePriceNotFoundException(PriceNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
