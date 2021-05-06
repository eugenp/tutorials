package com.example.todo.app.service;

import com.example.todo.app.domain.Task;
import java.util.List;

public interface TaskService {
    void addTask(Task task);

    boolean removeTask(int id);

    List<Task> findAllTasks();
}
