package com.baeldung.httpfirewall.advice;

import com.baeldung.httpfirewall.exception.DuplicateUserException;
import com.baeldung.httpfirewall.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * This advice handles all the generic exceptions related to HTTP (Ex: 400 - Bad Request, 403 - Forbidden, 401 - Unauthorized).
     *
     * @param responseStatusException This object contains all the information about the exception.
     * @return Returns the response object that will be sent to the user when a ResponseStatusException occurs.
     * @see ResponseStatusException
     */
    @ExceptionHandler({ ResponseStatusException.class })
    public ResponseEntity<Response> handleResponseStatusException(ResponseStatusException responseStatusException) {
        //@formatter:off
        return ResponseEntity.
          status(responseStatusException.getStatus()).
          body(Response.builder()
           .code(responseStatusException.getStatus().value())
           .message(responseStatusException.getReason())
           .timestamp(System.currentTimeMillis()).build());
        //@formatter:on
    }

    /**
     * This advice handles an exception that is thrown when trying to create a duplicate user
     *
     * @param duplicateUserException This object contains all the information about the exception.
     * @return Returns the response object that will be sent to the user when a ResponseStatusException occurs.
     */
    @ExceptionHandler({ DuplicateUserException.class })
    public ResponseEntity<Response> handleDuplicateUserException(DuplicateUserException duplicateUserException) {
        //@formatter:off
        return ResponseEntity
          .status(HttpStatus.BAD_REQUEST)
          .body(Response.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .message(duplicateUserException.getMessage())
            .timestamp(System.currentTimeMillis()).build());
        //@formatter:on
    }
}
