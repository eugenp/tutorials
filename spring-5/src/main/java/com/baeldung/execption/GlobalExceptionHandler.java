package com.baeldung.execption;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Comment this method to get Spring-boot's default Error Message for ResponseStatusException
    @ExceptionHandler(value = { ResponseStatusException.class })
    protected ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getReason(), ex.getCause()
            .getMessage());
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String reason, String message) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, reason, message);
        return new ResponseEntity<Object>(error, new HttpHeaders(), error.getStatus());
    }
}