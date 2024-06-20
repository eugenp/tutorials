package com.baeldung.athena.exception;

public class QueryExecutionFailureException extends RuntimeException {

    private static final long serialVersionUID = 4359781704223584247L;

    public QueryExecutionFailureException(String reason) {
        super(reason);
    }

}