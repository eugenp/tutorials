package com.baeldung.athena.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import software.amazon.awssdk.services.athena.model.InvalidRequestException;

@RestControllerAdvice
public class ExceptionResponseHandler {

    private static final String VIOLATIONS_KEY = "violations";

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handle(ResponseStatusException exception) {
        return ProblemDetail.forStatusAndDetail(exception.getStatusCode(), exception.getReason());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ProblemDetail handle(InvalidRequestException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid SQL query syntax.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handle(MethodArgumentNotValidException exception) {
        List<String> violations = new ArrayList<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            violations.add(fieldError.getDefaultMessage());
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failure.");
        problemDetail.setProperty(VIOLATIONS_KEY, violations);
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handle(Exception exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_IMPLEMENTED, "Something went wrong.");
    }

}