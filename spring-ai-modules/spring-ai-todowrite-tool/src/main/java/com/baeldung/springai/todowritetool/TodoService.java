package com.baeldung.springai.todowritetool;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TodoService {

    private final AtomicReference<List<TodoItem>> todos = new AtomicReference<>(List.of());

    public void write(List<TodoItem> updatedTodos) {
        todos.set(updatedTodos);
    }

    public List<TodoItem> read() {
        return todos.get();
    }
}
