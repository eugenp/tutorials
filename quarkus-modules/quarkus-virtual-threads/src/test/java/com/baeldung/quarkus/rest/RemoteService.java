package com.baeldung.quarkus.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "remote-service")
public interface RemoteService {
    @GET
    @Path("/greetings")
    default String greetings(){
        return "Mocked Greeting";
    };
}