package com.baeldung.springai.todowritetool;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Service;

@Service
public class TodoService {

    private final AtomicReference<List<TodoItem>> todos = new AtomicReference<>(List.of());

    public List<TodoItem> write(List<TodoItem> updatedTodos) {
        todos.set(updatedTodos);
        return updatedTodos;
    }

    public List<TodoItem> read() {
        return todos.get();
    }
}
