package com.baeldung.pattern.hexagonal.exception;

import org.springframework.http.HttpStatus;

public class RESTException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;
    private String errorCode;
    private String errorMessage;

    public RESTException(HttpStatus httpStatus, String errorCode, String message, Throwable e) {
        super(message, e);
        this.httpStatus = httpStatus;
        this.errorMessage = message;
    }

    public RESTException(HttpStatus httpStatus, String errorCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
