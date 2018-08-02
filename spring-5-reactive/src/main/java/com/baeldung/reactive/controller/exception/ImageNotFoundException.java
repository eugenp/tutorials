package com.baeldung.reactive.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends Exception {
    public ImageNotFoundException(String message) {
        super(message);
    }
}
