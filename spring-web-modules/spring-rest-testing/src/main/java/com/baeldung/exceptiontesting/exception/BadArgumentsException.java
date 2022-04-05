package com.baeldung.exceptiontesting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadArgumentsException extends RuntimeException {

    public BadArgumentsException(String message) {
        super(message);
    }
}
