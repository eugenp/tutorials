package com.baeldung.businesslogic.repository;

import com.baeldung.businesslogic.entity.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long> {
}
