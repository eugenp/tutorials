package com.baeldung.spring.reactive.customexception.exception;

import com.baeldung.spring.reactive.customexception.model.CustomErrorResponse;

import lombok.Getter;


public class CustomErrorException extends RuntimeException {
    @Getter
    private CustomErrorResponse errorResponse;

    public CustomErrorException(String message, CustomErrorResponse errorResponse) {
        super(message);
        this.errorResponse = errorResponse;
    }
}
