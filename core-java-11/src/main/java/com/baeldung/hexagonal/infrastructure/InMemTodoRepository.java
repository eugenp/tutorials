package java.com.baeldung.hexagonal.infrastructure;

import java.com.baeldung.hexagonal.domain.Todo;
import java.com.baeldung.hexagonal.domain.TodoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemTodoRepository implements TodoRepository {

    private List<Todo> list = new ArrayList<>();

    @Override
    public Todo save(Todo todo) {
        list.add(todo);
        return todo;

    }

    @Override
    public Optional<Todo> findById(Integer id) {
        return list.stream().filter(todo -> todo.getId() == id).findFirst();
    }
}
