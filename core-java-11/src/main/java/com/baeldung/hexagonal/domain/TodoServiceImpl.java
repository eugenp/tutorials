package java.com.baeldung.hexagonal.domain;

import java.time.LocalDateTime;

public class TodoServiceImpl implements TodoService {

    private TodoRepository repository;

    @Override
    public Todo create(String title, LocalDateTime dueDate){
        Todo todo = new Todo(title, dueDate);
        return repository.save(todo);
    }
}
