package com.baeldung.pattern.hexagonal.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RESTException {

        public ResourceNotFoundException(String errorCode, String message) {
                super(HttpStatus.NOT_FOUND, errorCode, message);
        }
}
