package com.baeldung.collectionsvsarrays;

public class Task {
    private final long id;
    private final int priority;
    private final String dueDate;

    public Task(final long id, int priority, String dueDate) {
        this.id = id;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return String.format("Task: #%-2d | Priority: %d | Due Date: %s", getId(), getPriority(), getDueDate());
    }

    public int getPriority() {
        return priority;
    }

    public String getDueDate() {
        return dueDate;
    }

    private long getId() {
        return id;
    }
}
