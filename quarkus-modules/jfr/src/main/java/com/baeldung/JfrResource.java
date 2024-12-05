package com.baeldung;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class JfrResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        DatabaseQueryEvent event = new DatabaseQueryEvent("SELECT * FROM users", 15);
        event.commit();
        return "hello";
    }

}
