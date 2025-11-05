package com.baeldung.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountAlreadyExistsException extends ResponseStatusException {

    private static final long serialVersionUID = 7559248156354211878L;

    public AccountAlreadyExistsException(final String reason) {
        super(HttpStatus.CONFLICT, reason);
    }

}