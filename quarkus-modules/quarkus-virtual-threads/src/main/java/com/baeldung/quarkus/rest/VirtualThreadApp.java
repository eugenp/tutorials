package com.baeldung.quarkus.rest;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/greetings")
public class VirtualThreadApp {
    @RestClient RemoteService service;
    @GET @RunOnVirtualThread
    public String process() {
        var response = service.greetings();
        return response.toUpperCase();
    }
}