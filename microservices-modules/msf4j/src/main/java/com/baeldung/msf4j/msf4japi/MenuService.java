package com.baeldung.msf4j.msf4japi;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/menu")
public class MenuService {

    private List<Meal> meals = new ArrayList<Meal>();
    
    public MenuService() {
        meals.add(new Meal("Java beans",42.0f));
    }
    
    @GET
    @Path("/")
    @Produces({ "application/json" })
    public Response index() {
        return Response.ok()
            .entity(meals)
            .build();
    }

    @GET
    @Path("/{id}")
    @Produces({ "application/json" })
    public Response meal(@PathParam("id") int id) {
        return Response.ok()
            .entity(meals.get(id))
            .build();
    }
    

    @POST
    @Path("/")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response create(Meal meal) {
        meals.add(meal);
        return Response.ok()
            .entity(meal)
            .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/json")
    @Produces({ "application/json" })
    public Response update(@PathParam("id") int id, Meal meal) {
        meals.set(id, meal);
        return Response.ok()
            .entity(meal)
            .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces({ "application/json" })
    public Response delete(@PathParam("id") int id) {
        Meal meal = meals.get(id);
        meals.remove(id);
        return Response.ok()
            .entity(meal)
            .build();
    }
}
