package java.com.baeldung.hexagonal.domain;

import java.time.LocalDateTime;

public interface TodoService {
    Todo create(String title, LocalDateTime dueDate);
}
