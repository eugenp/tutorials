package com.baeldung.gcp.firebase.auth;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidLoginCredentialsException extends ResponseStatusException {

    public InvalidLoginCredentialsException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }

}