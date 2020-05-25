package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.core.domain.Todo;
import com.baeldung.hexagonal.core.port.required.IPersistToDoPort;

import java.util.Collections;
import java.util.List;

public class InMemoryToDoAdapter implements IPersistToDoPort {

    private List<Todo> IN_MEMORY_TODO_LIST = Collections.emptyList();

    public void persistTodo(Todo item) {
        IN_MEMORY_TODO_LIST.add(item);
    }
}
