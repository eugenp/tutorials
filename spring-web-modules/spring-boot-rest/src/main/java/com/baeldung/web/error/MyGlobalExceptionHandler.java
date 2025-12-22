package com.baeldung.web.error;

import com.baeldung.web.exception.CustomException1;
import com.baeldung.web.exception.CustomException2;
import com.baeldung.web.exception.CustomException3;
import com.baeldung.web.exception.CustomException4;
import com.baeldung.web.exception.CustomException5;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException1.class)
    public void handleException1() {
        //
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ProblemDetail handleException2(CustomException2 ex) {
        return ProblemDetail
                .forStatusAndDetail(
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage()
                );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler( produces = MediaType.APPLICATION_JSON_VALUE )
    public CustomExceptionObject handleException3Json(CustomException3 ex) {
        return new CustomExceptionObject()
                .setMessage("custom exception 3: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler( produces = MediaType.TEXT_PLAIN_VALUE )
    public String handleException3Text(CustomException3 ex) {
        return "custom exception 3: " + ex.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            CustomException4.class,
            CustomException5.class
    })
    public ResponseEntity<CustomExceptionObject> handleException45(Exception ex) {
        return ResponseEntity
                .badRequest()
                .body(
                  new CustomExceptionObject()
                          .setMessage( "custom exception 4/5: " + ex.getMessage())
                );
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler( AccessDeniedException.class )
    public void handleAccessDeniedException() {
        // ...
    }

}
