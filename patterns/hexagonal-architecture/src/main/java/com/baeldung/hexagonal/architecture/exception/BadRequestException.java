package com.baeldung.hexagonal.architecture.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends Exception {

    String errorMessage;
    HttpStatus status;

    public BadRequestException(String errorMessage, HttpStatus status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }
}
