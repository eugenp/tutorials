package com.baeldung.hexagonal.core;

import java.time.LocalDate;

public class TodoItem {

    private String description;

    private LocalDate dueDate;

    private LocalDate creationDate;

    private boolean active;

    public TodoItem(String description, LocalDate dueDate, LocalDate creationDate) {
        this(description, dueDate, creationDate, true);
    }

    public TodoItem(String description, LocalDate dueDate, LocalDate creationDate, boolean active) {
        this.description = description;
        this.dueDate = dueDate;
        this.creationDate = creationDate;
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public boolean isActive() {
        return active;
    }
}
