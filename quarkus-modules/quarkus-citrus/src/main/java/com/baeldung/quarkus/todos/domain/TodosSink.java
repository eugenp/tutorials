package com.baeldung.quarkus.todos.domain;

import java.util.Collection;
import java.util.Optional;

public interface TodosSink {

    Collection<Todo> findAll();

    Optional<Todo> findById(Long id);

    void add(Todo todo);

}
