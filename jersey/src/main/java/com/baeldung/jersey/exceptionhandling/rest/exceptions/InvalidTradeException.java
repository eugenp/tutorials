package com.baeldung.jersey.exceptionhandling.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class InvalidTradeException extends WebApplicationException {
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "invalid trade operation";

    public InvalidTradeException() {
        super(MESSAGE, Response.Status.NOT_ACCEPTABLE);
    }

    public InvalidTradeException(String detail) {
        super(MESSAGE + ": " + detail, Response.Status.NOT_ACCEPTABLE);
    }
}
