package com.baeldung.architecture.framework.http.task;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class TaskRequest {
    @JsonInclude(Include.NON_NULL)
    private String description;

    @JsonInclude(Include.NON_NULL)
    private String dueDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
} 
