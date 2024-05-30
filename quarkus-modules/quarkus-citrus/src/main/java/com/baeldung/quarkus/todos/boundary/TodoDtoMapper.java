package com.baeldung.quarkus.todos.boundary;

import com.baeldung.quarkus.todos.domain.Todo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface TodoDtoMapper {

	TodoDto map(Todo source);

	Todo map(TodoDto source);

}
