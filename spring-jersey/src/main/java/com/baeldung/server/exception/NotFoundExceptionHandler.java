package com.baeldung.server.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionHandler implements ExceptionMapper<EmployeeNotFound> {
    public Response toResponse(EmployeeNotFound ex) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
