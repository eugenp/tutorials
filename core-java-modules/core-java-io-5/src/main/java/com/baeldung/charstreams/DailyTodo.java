package com.baeldung.charstreams;

import java.time.LocalDateTime;

public class DailyTodo {
    private String task;
    private LocalDateTime due;
    private boolean status;

    public DailyTodo(String task, LocalDateTime due, boolean status) {
        this.task = task;
        this.due = due;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task: " + task + "\nDue: " + due + "\nStatus: " + status+"\n";
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public LocalDateTime getDue() {
        return due;
    }

    public void setDue(LocalDateTime due) {
        this.due = due;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
