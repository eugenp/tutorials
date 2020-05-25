package com.baeldung.hexagonal.core.port.required;

import com.baeldung.hexagonal.core.domain.Todo;

public interface IPersistToDoPort {

    void persistTodo(Todo item);
}
