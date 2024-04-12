package com.baeldung.springbootmvc.jsfapplication.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class TodoDao implements Dao<Todo> {

    private List<Todo> todoList = new ArrayList<>();

    @Override
    public Optional<Todo> get(int id) {
        return Optional.ofNullable(todoList.get(id));
    }

    @Override
    public Collection<Todo> getAll() {
        return todoList.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    @Override
    public int save(Todo todo) {
        todoList.add(todo);
        int index = todoList.size() - 1;
        todo.setId(index);
        return index;
    }

    @Override
    public void update(Todo todo) {
        todoList.set(todo.getId(), todo);
    }

    @Override
    public void delete(Todo todo) {
        todoList.set(todo.getId(), null);
    }

}