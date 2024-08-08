package com.baeldung.httpstatuscode.exception;

import org.springframework.http.HttpStatusCode;

public class CustomException extends RuntimeException {

    private final HttpStatusCode statusCode;

    public CustomException(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }
}
