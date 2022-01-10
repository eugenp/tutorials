package com.example.springboot.repository;

import com.example.springboot.domain.Task;
import org.springframework.stereotype.Repository;

public interface TaskRepository {

    public Task createTask(Task task);
    public Task readTask(String id);
    public Task updateTask(Task task);
    public boolean deleteTask(String id);
}
