package com.baeldung.jetbrainsxodus;

public class TaskEntity {
    private final String description;
    private final String labels;

    public TaskEntity(String description, String labels) {
        this.description = description;
        this.labels = labels;
    }

    public String getDescription() {
        return description;
    }

    public String getLabels() {
        return labels;
    }
}
