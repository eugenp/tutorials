package com.baeldung.javaxval.childvalidation;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Project {

    @NotBlank(message = "Project title must be present")
    @Size(min = 3, max = 20, message = "Project title size not valid")
    private String title;

    @Valid
    private User owner;

    @Valid
    private List<Task> tasks;

    private Map<@Valid User, @Valid Task> assignedTask;

    private Map<String, List<@Valid Task>> taskByType;

    public Project(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Map<User, Task> getAssignedTask() {
        return assignedTask;
    }

    public void setAssignedTask(Map<User, Task> assignedTask) {
        this.assignedTask = assignedTask;
    }

    public Map<String, List<Task>> getTaskByType() {
        return taskByType;
    }

    public void setTaskByType(Map<String, List<Task>> taskByType) {
        this.taskByType = taskByType;
    }
}

