package com.baelding.springbootredis.exception.session;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SessionNotFoundException extends RuntimeException {
    private static final String MESSAGE_FORMAT = "Session with id=%s doesn't exists!";
    public SessionNotFoundException(String id) {
        super(String.format(MESSAGE_FORMAT, id));
    }
}
