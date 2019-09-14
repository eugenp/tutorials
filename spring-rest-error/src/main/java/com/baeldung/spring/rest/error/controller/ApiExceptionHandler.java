package com.baeldung.spring.rest.error.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.baeldung.spring.rest.error.exception.ApiException;
import com.baeldung.spring.rest.error.exception.BookNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound(BookNotFoundException ex, WebRequest request) {
        return handleApiException(ex, HttpStatus.NOT_FOUND, request);
    }
    
    private ResponseEntity<Object> handleApiException(ApiException ex, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, ex.getJson(), new HttpHeaders(), status, request);
    }
}
