package com.example.todo.app.repository;

import com.example.todo.app.domain.Task;
import java.util.List;

public interface TaskRepository {
    void add(Task task);

    boolean remove(int id);

    List<Task> findAll();
}
