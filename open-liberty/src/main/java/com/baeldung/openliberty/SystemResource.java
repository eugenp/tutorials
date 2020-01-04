package com.baeldung.openliberty;

import javax.ws.rs.core.Response;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@RequestScoped
@Path("/properties")
public class SystemResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "getPropertiesTime", description = "Time needed to get the JVM system properties")
    @Counted(absolute = true, description = "Number of times the JVM system properties are requested")
    public Response getProperties() {
        return Response.ok(System.getProperties()).build();
    }
}

