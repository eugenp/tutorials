package com.example.springboot.service;

import com.example.springboot.domain.Task;

public interface TaskService {

    public Task createTask(Task task);
    public Task readTask(String id);
    public Task updateTask(Task task);
    public boolean deleteTask(String id);
}
