package com.baeldung.web.error;

import com.baeldung.web.exception.CustomException3;
import com.baeldung.web.exception.CustomException4;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    // simple example for global exception handling
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException3.class)
    public void handleCustomException3() {
        //
    }

    // content negotiation
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProblemDetail handleCustomException4Json(CustomException4 ex) {
        String message = "custom exception 4: " + ex.getMessage();
        return ProblemDetail
                .forStatusAndDetail(HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()), message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(produces = MediaType.TEXT_PLAIN_VALUE)
    public String handleCustomException4Text(CustomException4 ex) {
        return "custom exception 4: " + ex.getMessage();
    }

}
