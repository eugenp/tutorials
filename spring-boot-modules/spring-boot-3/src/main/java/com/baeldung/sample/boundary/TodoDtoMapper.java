package com.baeldung.sample.boundary;

import com.baeldung.sample.control.Todo;
import org.mapstruct.Mapper;

import java.util.Locale;

/**
 * Dieser Mapper kopiert die Informationen zwischen den Schichten.
 */
@Mapper(componentModel = "spring")
interface TodoDtoMapper {

    TodoResponseDto map(Todo todo);

    default String _mapStatus(Todo.Status status) {
        return switch (status) {
            case NEW -> "new";
            case PROGRESS -> "progress";
            case COMPLETED -> "completed";
            case ARCHIVED -> "archived";
        };
    }

    Todo map(TodoRequestDto todo, Long id);

    default Todo.Status _mapStatus(String status) {
        return null == status ? Todo.Status.NEW : switch (status) {
            case "progress" -> Todo.Status.PROGRESS;
            case "completed" -> Todo.Status.COMPLETED;
            case "archived" -> Todo.Status.ARCHIVED;
            default -> Todo.Status.NEW;
        };
    }

}
