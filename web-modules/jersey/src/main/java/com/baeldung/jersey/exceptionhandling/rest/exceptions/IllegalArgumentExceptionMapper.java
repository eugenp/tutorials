package com.baeldung.jersey.exceptionhandling.rest.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    public static final String DEFAULT_MESSAGE = "an illegal argument was provided";

    @Override
    public Response toResponse(final IllegalArgumentException exception) {
        return Response.status(Response.Status.EXPECTATION_FAILED)
            .entity(build(exception.getMessage()))
            .type(MediaType.APPLICATION_JSON)
            .build();
    }

    private RestErrorResponse build(String message) {
        RestErrorResponse response = new RestErrorResponse();
        response.setMessage(DEFAULT_MESSAGE + ": " + message);
        return response;
    }
}
