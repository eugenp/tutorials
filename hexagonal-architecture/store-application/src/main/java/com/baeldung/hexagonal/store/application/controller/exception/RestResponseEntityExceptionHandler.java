package com.baeldung.hexagonal.store.application.controller.exception;

import com.baeldung.hexagonal.store.core.context.customer.exception.CustomerNotFoundException;
import com.baeldung.hexagonal.store.core.context.customer.exception.NotEnoughFundsException;
import com.baeldung.hexagonal.store.core.context.customer.exception.OrderNotFoundException;
import com.baeldung.hexagonal.store.core.context.order.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {ProductNotFoundException.class, CustomerNotFoundException.class})
    protected ResponseEntity<JsonResponse> handleNotFoundException(
            RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(new JsonResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {OrderNotFoundException.class, NotEnoughFundsException.class})
    protected ResponseEntity<JsonResponse> handleBadRequestException(
            RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(new JsonResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private static class JsonResponse {
        String message;

        public JsonResponse() {
        }

        public JsonResponse(String message) {
            super();
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}