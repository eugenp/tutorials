package com.baeldung.jnosql.artemis;

import com.baeldung.jnosql.artemis.qualifier.Repo;
import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Repo
public class RepositoryTodoManager implements TodoManager {

    @Inject
    @Database(DatabaseType.DOCUMENT)
    private TodoRepository repository;

    @Override
    public Todo add(Todo todo) {
        return repository.save(todo);
    }

    @Override
    public Todo get(String id) {
        Optional<Todo> todo = repository.findById(id);
        return todo.get();
    }

    @Override
    public List<Todo> getAll() {
        return repository.findAll();
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

}
