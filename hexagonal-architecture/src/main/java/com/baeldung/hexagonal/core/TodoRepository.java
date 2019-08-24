package com.baeldung.hexagonal.core;

import java.util.Optional;

public interface TodoRepository {

    Optional<TodoItem> getActiveTodoItemByDescription(String description);

    void save(TodoItem item);
}
