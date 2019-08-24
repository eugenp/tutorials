package com.baeldung.hexagonal.core;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

public class TodoService {

    private Supplier<LocalDate> currentDateProvider;

    private TodoRepository todoRepository;

    public TodoService(Supplier<LocalDate> currentDateProvider, TodoRepository todoRepository) {
        this.currentDateProvider = currentDateProvider;
        this.todoRepository = todoRepository;
    }

    public void addTodoItem(String description, LocalDate dueDate) throws TodoException {
        LocalDate currentDate = currentDateProvider.get();
        if (currentDate.compareTo(dueDate) > 0) {
            throw new PastDueDateException(currentDate, dueDate);
        }
        Optional<TodoItem> maybeExistingItem = todoRepository.getActiveTodoItemByDescription(description);
        if (maybeExistingItem.isPresent()) {
            throw new TodoItemAlreadyExistsException(description);
        }
        TodoItem item = new TodoItem(description, dueDate, currentDate);
        todoRepository.save(item);
    }

}
