package com.baeldung.texttosql;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class EmptyResultException extends ResponseStatusException {

    EmptyResultException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }

}