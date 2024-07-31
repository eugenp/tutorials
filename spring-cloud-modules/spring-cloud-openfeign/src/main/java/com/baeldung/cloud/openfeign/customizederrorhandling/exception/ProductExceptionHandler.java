package com.baeldung.cloud.openfeign.customizederrorhandling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ProductExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ProductServiceNotAvailableException.class})
    public ResponseEntity<ErrorResponse> handleProductServiceNotAvailableException(ProductServiceNotAvailableException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(
          HttpStatus.INTERNAL_SERVER_ERROR,
          exception.getMessage(),
          request.getDescription(false)),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(
          HttpStatus.NOT_FOUND,
          exception.getMessage(),
          request.getDescription(false)),
          HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(
          HttpStatus.INTERNAL_SERVER_ERROR,
          exception.getMessage(),
          request.getDescription(false)),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
