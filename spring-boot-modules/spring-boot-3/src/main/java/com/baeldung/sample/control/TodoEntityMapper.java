package com.baeldung.sample.control;

import com.baeldung.sample.entity.TodoEntity;
import org.mapstruct.Mapper;

/**
 * Dieser Mapper kopiert die Informationen zwischen den Schichten.
 */
@Mapper(componentModel = "spring")
interface TodoEntityMapper {

    TodoEntity map(Todo todo);

    Todo map(TodoEntity todo);

}
