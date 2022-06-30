package com.baeldung.quarkus.hello;

import com.baeldung.quarkus.hello.service.HelloService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {

    @Inject
    HelloService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        StringBuilder sb = new StringBuilder();
        sb.append("Those are saying hello:\n=======================\n\n");
        service.sendHello(s -> sb.append(" - ").append(s).append("\n"));
        return sb.toString();
    }

}
