package com.baeldung.businesslogic.service;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import com.baeldung.businesslogic.annotation.CanUpdateTodo;
import com.baeldung.businesslogic.entity.Todo;
import com.baeldung.businesslogic.repository.TodoRepository;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @CanUpdateTodo
    public Todo save(@P("todo") Todo task) {
        return this.todoRepository.save(task);
    }
}
