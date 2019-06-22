package com.baeldung.hexagonal.api.controller;

import com.baeldung.hexagonal.api.dto.ErrorDto;
import com.baeldung.hexagonal.core.domain.exception.InvalidPostActionException;
import com.baeldung.hexagonal.core.domain.exception.PostAlreadyExistsException;
import com.baeldung.hexagonal.core.domain.exception.PostNotFoundException;
import com.baeldung.hexagonal.core.domain.exception.PostServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.baeldung.hexagonal.api.controller")
public class PostControllerAdvice {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ResponseEntity<ErrorDto> handleRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {

        String error = "HTTP method "
                .concat(ex.getMethod())
                .concat("not allowed; supported methods: ")
                .concat(Arrays.toString(ex.getSupportedMethods()));

        ErrorDto err = new ErrorDto(HttpStatus.METHOD_NOT_ALLOWED, error);
        return errResponse(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        String errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(","));

        ErrorDto err = new ErrorDto(HttpStatus.BAD_REQUEST, errors);
        return errResponse(err);
    }

    @ExceptionHandler(InvalidPostActionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorDto> handleNotFound(InvalidPostActionException ex) {
        ErrorDto err = new ErrorDto(HttpStatus.BAD_REQUEST, ex.getMessage());
        return errResponse(err);
    }
    
    @ExceptionHandler(PostAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ResponseEntity<ErrorDto> handleAlreadyExists(PostAlreadyExistsException ex) {
        ErrorDto err = new ErrorDto(HttpStatus.CONFLICT, ex.getMessage());
        return errResponse(err);
    }

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ErrorDto> handleNotFound(PostNotFoundException ex) {
        ErrorDto err = new ErrorDto(HttpStatus.NOT_FOUND, ex.getMessage());
        return errResponse(err);
    }

    @ExceptionHandler(PostServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorDto> handleInternalErrors(PostServiceException ex) {
        ErrorDto err = new ErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return errResponse(err);
    }

    private ResponseEntity<ErrorDto> errResponse(ErrorDto err) {
        return ResponseEntity.status(err.getStatus()).body(err);
    }
}
