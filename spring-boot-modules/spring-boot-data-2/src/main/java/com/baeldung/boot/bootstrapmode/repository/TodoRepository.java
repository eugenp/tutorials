package com.baeldung.boot.bootstrapmode.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.boot.bootstrapmode.domain.Todo;

public interface TodoRepository extends CrudRepository<Todo, Long> {
}
