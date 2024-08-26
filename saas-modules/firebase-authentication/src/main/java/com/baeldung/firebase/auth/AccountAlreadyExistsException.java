package com.baeldung.firebase.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountAlreadyExistsException extends ResponseStatusException {

    private static final long serialVersionUID = -8388485899470907402L;

    public AccountAlreadyExistsException(String reason) {
        super(HttpStatus.CONFLICT, reason);
    }

}