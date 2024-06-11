package com.baeldung.quarkus.rbac.api;

import com.baeldung.quarkus.rbac.users.errors.DomainDataException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApiErrorHandler implements ExceptionMapper<DomainDataException> {

    @Override
    public Response toResponse(final DomainDataException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
          .entity(exception.getEntity() == null? exception.getEntity() : new Message(exception.getMessage()))
          .build();
    }
}
