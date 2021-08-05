package com.baeldung.pattern.hexagonal.boundary;

import com.baeldung.pattern.hexagonal.domain.ports.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected void handleNotFoundException() {
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(code = BAD_REQUEST)
    protected void handleValidationException() {
    }

}
