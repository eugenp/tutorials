package com.baeldung.quarkus.todos.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class TodosRepository implements PanacheRepository<TodoEntity> {

}
