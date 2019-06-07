package com.baeldung.hexagonal.architecture.controllers;

import com.baeldung.hexagonal.architecture.exception.BadRequestException;
import com.baeldung.hexagonal.architecture.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BaseController {

    @ExceptionHandler(value = BadRequestException.class)
    protected ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        return error(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    protected ResponseEntity<String> handleNotFoundException(NotFoundException e) {
        return error(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<String> error(final String errorMessage, final HttpStatus httpStatus) {
        return new ResponseEntity<String>(errorMessage, httpStatus);

    }


}
