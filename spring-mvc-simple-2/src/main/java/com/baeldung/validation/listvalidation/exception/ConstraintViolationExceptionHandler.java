package com.baeldung.validation.listvalidation.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ConstraintViolationExceptionHandler {
    private final Logger LOGGER = LoggerFactory.getLogger(ConstraintViolationExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handle(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            builder.append(violation.getMessage());

        }
        LOGGER.error(builder.toString());
        return new ResponseEntity<String>(builder.toString(), HttpStatus.BAD_REQUEST);
    }
}
