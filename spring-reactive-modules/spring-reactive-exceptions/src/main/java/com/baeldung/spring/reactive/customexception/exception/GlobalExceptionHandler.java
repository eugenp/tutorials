package com.baeldung.spring.reactive.customexception.exception;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

import com.baeldung.spring.reactive.customexception.model.CustomErrorResponse;
import com.baeldung.spring.reactive.customexception.model.ErrorDetails;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    protected ProblemDetail handleNotFound(RuntimeException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("User not found");
        problemDetail.setType(URI.create("https://example.com/problems/user-not-found"));
        problemDetail.setProperty("errors", List.of(ErrorDetails.API_USER_NOT_FOUND));
        return problemDetail;
    }

    @ExceptionHandler(CustomErrorException.class)
    protected ResponseEntity<CustomErrorResponse> handleCustomError(RuntimeException ex) {
        CustomErrorException customErrorException = (CustomErrorException) ex;
        return ResponseEntity.status(customErrorException.getErrorResponse().getStatus())
          .body(customErrorException.getErrorResponse());
    }

}
