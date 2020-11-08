package com.baeldung.maven.it;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/")
public class RestEndpoint {
    @GET
    public String hello() {
        return "Welcome to Baeldung!";
    }
}
