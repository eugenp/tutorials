package java.com.baeldung.hexagonal.domain;

import java.time.LocalDateTime;

public class Todo {
    private int id;
    private String title;
    private LocalDateTime dueDate;

    public Todo(String title, LocalDateTime dueDate) {
        this.title = title;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }


    // getters and setters
}
