package com.baeldung.javaxval.childvalidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Task {

    @NotBlank(message = "Task title must be present")
    @Size(min = 3, max = 20, message = "Task title size not valid")
    private String title;

    @Size(min = 3, max = 500, message = "Task description size not valid")
    private String description;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}