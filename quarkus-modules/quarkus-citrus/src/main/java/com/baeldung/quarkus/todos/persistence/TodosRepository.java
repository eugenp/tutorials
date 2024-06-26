package com.baeldung.quarkus.todos.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
@Transactional
public class TodosRepository implements PanacheRepository<TodoEntity> {

}
