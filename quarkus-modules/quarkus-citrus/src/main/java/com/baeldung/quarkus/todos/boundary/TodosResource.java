package com.baeldung.quarkus.todos.boundary;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import com.baeldung.quarkus.todos.domain.Todo;
import com.baeldung.quarkus.todos.domain.TodosService;

import lombok.RequiredArgsConstructor;

@Path("/api/v1/todos") // we need "api/v1" here for Quarkus tests to run correctly
@RequiredArgsConstructor
public class TodosResource {

    private final TodosService service;
    private final TodoDtoMapper mapper;

    @GET
    public Collection<TodoDto> findAll() {
        return service.findAll()
            .stream()
            .map(mapper::map)
            .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    public TodoDto findById(@PathParam("id") @Min(0) Long id) {
        return service.findById(id)
            .map(mapper::map)
            .orElseThrow(NotFoundException::new);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid TodoDto todoDto, @Context UriInfo info) {
        Todo todo = mapper.map(todoDto);
        service.add(todo);
        // create response
        URI location = info.getAbsolutePathBuilder() // current URL
            .path(Long.toString(todo.getId())) // add "/<id>"
            .build();
        return Response.created(location)
            .entity(todo)
            .build();

    }

}
