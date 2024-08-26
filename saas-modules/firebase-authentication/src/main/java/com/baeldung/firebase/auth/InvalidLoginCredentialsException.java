package com.baeldung.firebase.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidLoginCredentialsException extends ResponseStatusException {

    private static final long serialVersionUID = -196939539846506502L;

    public InvalidLoginCredentialsException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }

}