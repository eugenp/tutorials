package com.baeldung.quarkus;

import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello")
@RequestScoped
public interface MyService {

    @GET
    @Produces(TEXT_PLAIN)
    String hello();
}
