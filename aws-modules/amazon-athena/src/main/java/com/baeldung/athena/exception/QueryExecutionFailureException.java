package com.baeldung.athena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class QueryExecutionFailureException extends ResponseStatusException {

    private static final long serialVersionUID = 4359781704223584247L;
    
    private static final String DEFAULT_MESSAGE = "Query execution failed. Validate the query before resubmitting.";

    public QueryExecutionFailureException() {
        super(HttpStatus.BAD_REQUEST, DEFAULT_MESSAGE);
    }

}