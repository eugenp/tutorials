package com.baeldung.jersey.client.listdemo;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("/")
public class JerseyListDemo {
    @GET
    public String getItems(@QueryParam("items") List<String> items) {
        return "Received items: " + items;
    }
}
