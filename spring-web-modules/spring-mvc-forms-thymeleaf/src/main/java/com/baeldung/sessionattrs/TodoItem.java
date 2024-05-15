package com.baeldung.sessionattrs;

import java.time.LocalDateTime;

public class TodoItem {

    private String description;
    private LocalDateTime createDate;

    public TodoItem(String description, LocalDateTime createDate) {
        this.description = description;
        this.createDate = createDate;
    }

    public TodoItem() {
        // default no arg constructor
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "TodoItem [description=" + description + ", createDate=" + createDate + "]";
    }
}
