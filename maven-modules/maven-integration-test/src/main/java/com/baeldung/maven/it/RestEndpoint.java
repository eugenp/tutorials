package com.baeldung.maven.it;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/")
public class RestEndpoint {
    @GET
    public String hello() {
        return "Welcome to Baeldung!";
    }
}
