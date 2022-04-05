package com.baeldung.jnosql.artemis;

import org.jnosql.artemis.Repository;

import java.util.List;

public interface TodoRepository extends Repository<Todo, String> {
    List<Todo> findByName(String name);
    List<Todo> findAll();
}
