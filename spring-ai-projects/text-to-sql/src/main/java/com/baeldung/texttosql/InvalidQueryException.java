package com.baeldung.texttosql;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class InvalidQueryException extends ResponseStatusException {

	InvalidQueryException(String reason) {
		super(HttpStatus.BAD_REQUEST, reason);
	}

}