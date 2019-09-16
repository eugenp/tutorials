package com.baeldung.spring.rest.error.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.baeldung.spring.rest.error.exception.BookNotFoundException;

@RestControllerAdvice
public class ApiExceptionHandler {
    
    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleApiException(BookNotFoundException ex) {
        return ex.toJson();
    }
}
