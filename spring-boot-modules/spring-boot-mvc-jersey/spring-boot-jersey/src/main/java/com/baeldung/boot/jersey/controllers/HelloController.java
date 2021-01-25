package com.baeldung.boot.jersey.controllers;

import java.awt.PageAttributes.MediaType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.xml.ws.Response;

@Path("/hello")
public class HelloController {

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello(@PathParam("name") String name) {
        return Response.ok("Hello, " + name)
            .build();
    }

}
