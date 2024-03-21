package de.samples.quarkus.todos.boundary;

import java.util.Collection;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import de.samples.quarkus.todos.domain.TodosService;
import lombok.var;

@Path("/api/v1/todos") // we need "api/v1" here for Quarkus tests to run correctly
public class TodosResource {

	private final TodosService service;
	private final TodoDtoMapper mapper;

	public TodosResource(TodosService service, TodoDtoMapper mapper) {
		super();
		this.service = service;
		this.mapper = mapper;
	}

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
	public Response create(@Valid TodoDto todoDto, @Context UriInfo info) {
		var todo = mapper.map(todoDto);
		service.add(todo);
		// create response
		var location = info.getAbsolutePathBuilder() // current URL
				.path(Long.toString(todo.getId())) // add "/<id>"
				.build();
		return Response.created(location).build();

	}

}
