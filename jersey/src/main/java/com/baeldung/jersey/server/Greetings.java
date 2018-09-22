package com.baeldung.jersey.server;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.baeldung.jersey.server.config.HelloBinding;

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
        return Response.status(Status.OK.getStatusCode())
            .build();
    }
}
