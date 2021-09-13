package com.baeldung.boot.hexagonal.application.advice;

import com.baeldung.boot.hexagonal.application.api.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler({ EmptyResultDataAccessException.class, MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Response> handleEmptyResultHandler(EmptyResultDataAccessException emptyResultDataAccessException) {
        log.info("No book found with given id ", emptyResultDataAccessException);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response(HttpStatus.NOT_FOUND.value(), "The book with given id is not found"));
    }

    @ExceptionHandler({ ResponseStatusException.class })
    public ResponseEntity<Response> handleResponseStatusException(ResponseStatusException responseStatusException) {
        log.info("Response status exception", responseStatusException);
        return ResponseEntity.status(responseStatusException.getStatus()).body(new Response(responseStatusException.getStatus().value(), responseStatusException.getReason()));
    }

}
