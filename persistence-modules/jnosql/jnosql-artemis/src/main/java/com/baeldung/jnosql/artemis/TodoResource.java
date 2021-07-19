package com.baeldung.jnosql.artemis;

import com.baeldung.jnosql.artemis.qualifier.Repo;
import com.baeldung.jnosql.artemis.qualifier.Template;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("todos")
public class TodoResource {

    /*
    Use eiher @Template or @Repo
     */
    @Inject
    @Template
    //@Repo
    private TodoManager todoManager;

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response all() {
        return Response.ok(todoManager.getAll()).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("id") String id) {
        Todo todo = todoManager.get(id);
        return Response.ok(todo).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Todo todo) {
        Todo savedTodo = todoManager.add(todo);
        System.out.println(savedTodo.id);
        return Response.created(
                UriBuilder.fromResource(this.getClass()).path(String.valueOf(savedTodo.id)).build())
                .build();
    }

}
