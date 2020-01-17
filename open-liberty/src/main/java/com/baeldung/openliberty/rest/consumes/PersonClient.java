package com.baeldung.openliberty.rest.consumes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "personClient", baseUri = "http://localhost:9080/")
public interface PersonClient {

    @GET
    @Path("/api/person")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPerson();
}
