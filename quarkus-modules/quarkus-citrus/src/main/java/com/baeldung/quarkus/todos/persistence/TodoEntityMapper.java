package com.baeldung.quarkus.todos.persistence;

import org.mapstruct.Mapper;

import com.baeldung.quarkus.todos.domain.Todo;

@Mapper(componentModel = "cdi")
public interface TodoEntityMapper {

    TodoEntity map(Todo source);

    Todo map(TodoEntity source);

}
