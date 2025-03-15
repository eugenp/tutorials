package com.baeldung.quarkus;

import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import io.quarkus.vertx.http.ManagementInterface;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    public void registerManagementRoutes(@Observes ManagementInterface mi) {
        mi.router()
            .get("/q/custom")
            .handler(rc -> rc.response()
                .end("Custom Management Endpoint Active"));
    }
}
