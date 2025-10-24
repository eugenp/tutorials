package com.baeldung.jersey.serverlogging;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/logging")
public class LoggingResource {

    @GET
    public String get() {
        return "Hello";
    }
}
