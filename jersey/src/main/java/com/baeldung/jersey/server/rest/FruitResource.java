package com.baeldung.jersey.server.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.mvc.ErrorTemplate;
import org.glassfish.jersey.server.mvc.Template;
import org.glassfish.jersey.server.mvc.Viewable;

import com.baeldung.jersey.server.constraints.SerialNumber;
import com.baeldung.jersey.server.model.Fruit;
import com.baeldung.jersey.service.SimpleStorageService;

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

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createFruit(
        @NotNull(message = "Fruit name must not be null") @FormParam("name") String name, 
        @NotNull(message = "Fruit colour must not be null") @FormParam("colour") String colour) {

        Fruit fruit = new Fruit(name, colour);
        SimpleStorageService.storeFruit(fruit);
    }
    
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateFruit(@SerialNumber @FormParam("serial") String serial) {
        Fruit fruit = new Fruit();
        fruit.setSerial(serial);
        SimpleStorageService.storeFruit(fruit);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createFruit(@Valid Fruit fruit) {
        SimpleStorageService.storeFruit(fruit);
    }
    
    @POST
    @Path("/created")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewFruit(@Valid Fruit fruit) {
        String result = "Fruit saved : " + fruit;
        return Response.status(Status.CREATED.getStatusCode())
            .entity(result)
            .build();
    }

    @GET
    @Valid
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search/{name}")
    public Fruit findFruitByName(@PathParam("name") String name) {
        return SimpleStorageService.findByName(name);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/exception")
    @Valid
    public Fruit exception() {
        Fruit fruit = new Fruit();
        fruit.setName("a");
        fruit.setColour("b");
        return fruit;
    }
}
