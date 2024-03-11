package de.samples.quarkus.todos.boundary;

import org.mapstruct.Mapper;

import de.samples.quarkus.todos.domain.Todo;

@Mapper(componentModel = "cdi")
public interface TodoDtoMapper {

	TodoDto map(Todo source);

	Todo map(TodoDto source);

}
