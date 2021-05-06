package com.example.todo.app.repository;

import com.example.todo.app.domain.Task;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private List<Task> tasks = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void add(Task task) {
        tasks.add(task);
    }

    @Override
    public boolean remove(int id) {
        return tasks.removeIf(task -> task.getId() == id);
    }

    @Override
    public List<Task> findAll() {
        return tasks;
    }
}
