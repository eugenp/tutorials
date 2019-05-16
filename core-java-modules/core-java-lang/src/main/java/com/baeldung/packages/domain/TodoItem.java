package com.baeldung.packages.domain;

import java.time.LocalDate;

public class TodoItem {
    private Long id;
    private String description;
    private LocalDate dueDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "TodoItem [id=" + id + ", description=" + description + ", dueDate=" + dueDate + "]";
    }

}
