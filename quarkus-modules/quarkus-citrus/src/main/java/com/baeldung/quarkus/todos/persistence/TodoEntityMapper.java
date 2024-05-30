package com.baeldung.quarkus.todos.persistence;

import com.baeldung.quarkus.todos.domain.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface TodoEntityMapper {

	TodoEntity map(Todo source);

	Todo map(TodoEntity source);

}
