package com.baeldung.jersey.exceptionhandling.rest.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

public class ServerExceptionMapper implements ExceptionMapper<WebApplicationException> {
    public static final String HTTP_405_MESSAGE = "METHOD_NOT_ALLOWED";

    @Override
    public Response toResponse(final WebApplicationException exception) {
        String message = exception.getMessage();
        Response response = exception.getResponse();
        Status status = response.getStatusInfo()
            .toEnum();

        switch (status) {
        case METHOD_NOT_ALLOWED:
            message = HTTP_405_MESSAGE;
            break;
        case INTERNAL_SERVER_ERROR:
            message = "internal validation - " + exception;
            break;
        default:
            message = "[unhandled response code] " + exception;
        }

        return Response.status(status)
            .entity(status + ": " + message)
            .type(MediaType.TEXT_PLAIN)
            .build();
    }
}
