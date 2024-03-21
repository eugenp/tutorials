package de.samples.quarkus.todos.persistence;

import org.mapstruct.Mapper;

import de.samples.quarkus.todos.domain.Todo;

@Mapper(componentModel = "cdi")
public interface TodoEntityMapper {

	TodoEntity map(Todo source);

	Todo map(TodoEntity source);

}
