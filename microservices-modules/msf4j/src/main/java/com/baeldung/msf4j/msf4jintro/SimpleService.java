package com.baeldung.msf4j.msf4jintro;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/")
public class SimpleService {

    @GET
    public String index() {
        return "Default content";
    }

    @GET
    @Path("/say/{name}")
    public String say(@PathParam("name") String name) {
        return "Hello " + name;
    }

}
