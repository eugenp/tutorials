package com.baeldung.jersey.server.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

import com.baeldung.jersey.server.model.Fruit;

@Path("/fruit")
public class FruitResource {

    @GET
    public Viewable get() {
        return new Viewable("/index.ftl", "Fruit Index Page");
    }

    @GET
    @Template(name = "/all.ftl")
    @Path("/all")
    @Produces(MediaType.TEXT_HTML)
    public Map<String, Object> getAllFruit() {
        final List<Fruit> fruits = new ArrayList<>();
        fruits.add(new Fruit("banana", "yellow"));
        fruits.add(new Fruit("apple", "red"));
        fruits.add(new Fruit("kiwi", "green"));

        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("items", fruits);
        return model;
    }

    @GET
    @ErrorTemplate(name = "/error.ftl")
    @Template(name = "/named.ftl")
    @Path("{name}")
    @Produces(MediaType.TEXT_HTML)
    public String getFruitByName(@PathParam("name") String name) {
        if (!"banana".equalsIgnoreCase(name)) {
            throw new IllegalArgumentException("Fruit not found: " + name);
        }
        return name;
    }

}
