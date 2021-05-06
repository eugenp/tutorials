package com.example.todo.app.service;

import com.example.todo.app.domain.Task;
import com.example.todo.app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Override
    public void addTask(Task task) {
        taskRepository.add(task);
    }

    @Override
    public boolean removeTask(int id) {
        return taskRepository.remove(id);
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }
}
