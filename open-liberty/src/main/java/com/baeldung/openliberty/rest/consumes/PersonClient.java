package com.baeldung.openliberty.rest.consumes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "personClient", baseUri = "http://localhost:9080/")
@RegisterProvider(UriNotFoundExceptionMapper.class)
@Path("/api/person/1")
public interface PersonClient extends AutoCloseable {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPerson() throws UriNotFoundException, ProcessingException;
}
