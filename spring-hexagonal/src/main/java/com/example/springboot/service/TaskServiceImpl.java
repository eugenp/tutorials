package com.example.springboot.service;

import com.example.springboot.domain.Task;
import com.example.springboot.repository.TaskRepository;
import com.example.springboot.repository.mongodb.TaskMongoDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        return taskRepository.createTask(task);
    }

    @Override
    public Task readTask(String id) {
        return taskRepository.readTask(id);
    }

    @Override
    public Task updateTask(Task task) {
        return taskRepository.updateTask(task);
    }

    @Override
    public boolean deleteTask(String id) {
        return taskRepository.deleteTask(id);
    }
}
