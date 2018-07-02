package com.baeldung.jnosql.artemis;

import org.jnosql.artemis.Database;
import org.jnosql.artemis.DatabaseType;
import org.jnosql.artemis.Repository;
import org.jnosql.artemis.document.DocumentTemplate;
import org.jnosql.diana.api.document.DocumentQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.jnosql.diana.api.document.query.DocumentQueryBuilder.select;

@ApplicationScoped
public class DocumentTodoManager implements TodoManager {

    private static final String TODO = Todo.class.getSimpleName();

    @Inject
    private DocumentTemplate documentTemplate;

    @Inject
    @Database(DatabaseType.DOCUMENT)
    private TodoRepository repository;

    @Override
    public Todo add(Todo todo) {
        return documentTemplate.insert(todo);
    }

    @Override
    public Todo get(String id) {
        Optional<Todo> todo= repository.findById(id);
        //Optional<Todo> todo= documentTemplate.find(Todo.class,id);
        return todo.get();
    }

    @Override
    public List<Todo> getAll() {
        DocumentQuery query = select().from(TODO).build();
        return documentTemplate.select(query);
    }
}
