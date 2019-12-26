package com.baeldung.jnosql.artemis;

import com.baeldung.jnosql.artemis.qualifier.Template;
import org.jnosql.artemis.document.DocumentTemplate;
import org.jnosql.diana.api.document.DocumentQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.jnosql.diana.api.document.query.DocumentQueryBuilder.select;

@ApplicationScoped
@Template
public class TemplateTodoManager implements TodoManager {

    @Inject
    private DocumentTemplate documentTemplate;

    @Override
    public Todo add(Todo todo) {
        return documentTemplate.insert(todo);
    }

    @Override
    public Todo get(String id) {
        Optional<Todo> todo = documentTemplate.find(Todo.class, id);
        return todo.get();
    }

    @Override
    public List<Todo> getAll() {
        DocumentQuery query = select().from("Todo").build();
        return documentTemplate.select(query);
    }

    @Override
    public void delete(String id) {
        documentTemplate.delete(Todo.class, id);
    }

}
