package com.baeldung.jersey.server;

import com.baeldung.jersey.server.config.HelloBinding;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/greetings")
public class Greetings {

    @GET
    @HelloBinding
    public String getHelloGreeting() {
        return "hello";
    }

    @GET
    @Path("/hi")
    public String getHiGreeting() {
        return "hi";
    }

    @POST
    @Path("/custom")
    public Response getCustomGreeting(String name) {
        return Response.status(Response.Status.OK.getStatusCode())
            .build();
    }
}
