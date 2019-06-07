package com.baeldung.hexagonal.architecture.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends  Exception {
    String errorMessage;
    HttpStatus status;

    public NotFoundException(String errorMessage, HttpStatus status) {
        this.errorMessage = errorMessage;
        this.status = status;
    }
}
