package com.baeldung.quarkus.todos.boundary;

import org.mapstruct.Mapper;

import com.baeldung.quarkus.todos.domain.Todo;

@Mapper(componentModel = "cdi")
public interface TodoDtoMapper {

    TodoDto map(Todo source);

    Todo map(TodoDto source);

}
