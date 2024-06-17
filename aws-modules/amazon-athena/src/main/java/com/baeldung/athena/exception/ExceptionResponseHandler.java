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

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.athena.model.InvalidRequestException;

@Slf4j
@RestControllerAdvice
public class ExceptionResponseHandler {

    private static final String VIOLATIONS_KEY = "violations";

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handle(ResponseStatusException exception) {
        log(exception);
        return ProblemDetail.forStatusAndDetail(exception.getStatusCode(), exception.getReason());
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ProblemDetail handle(InvalidRequestException exception) {
        log(exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid SQL query syntax.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handle(MethodArgumentNotValidException exception) {
        log(exception);
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
        log(exception);
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_IMPLEMENTED, "Something went wrong.");
    }

    private void log(final Exception exception) {
        log.error("Exception encountered: {}", exception.getMessage(), exception);
    }

}