package com.baeldung.quarkus.todos.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;

import com.baeldung.quarkus.todos.domain.Todo;
import com.baeldung.quarkus.todos.domain.TodosSink;

import lombok.RequiredArgsConstructor;

@ApplicationScoped
@Typed(TodosSink.class)
@RequiredArgsConstructor
public class TodosSinkJpaImpl implements TodosSink {

    private final TodosRepository repo;
    private final TodoEntityMapper mapper;

    @Override
    public Collection<Todo> findAll() {
        return repo.listAll()
            .stream()
            .map(mapper::map)
            .collect(Collectors.toList());

    }

    @Override
    public Optional<Todo> findById(Long id) {
        return repo.findByIdOptional(id)
            .map(mapper::map);
    }

    @Override
    public void add(Todo todo) {
        TodoEntity entity = mapper.map(todo);
        repo.persist(entity);
        todo.setId(entity.getId());
    }

}
