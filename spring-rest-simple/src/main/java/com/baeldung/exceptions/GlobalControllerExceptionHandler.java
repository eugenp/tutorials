package com.baeldung.exceptions;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<String> handleConflict(RuntimeException ex) {
        // Remove the try-catch block in the StringToEnumConverter if we want to handle the exception here
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
