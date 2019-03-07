package java.com.baeldung.hexagonal.domain;

import java.com.baeldung.hexagonal.domain.Todo;
import java.util.Optional;

public interface TodoRepository {
    Todo save(Todo todo);

    Optional<Todo> findById(Integer id);

}
