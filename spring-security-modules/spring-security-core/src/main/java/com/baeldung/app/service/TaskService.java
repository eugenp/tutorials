package com.baeldung.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import com.baeldung.app.entity.Task;
import com.baeldung.app.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @PostFilter("hasRole('MANAGER') or filterObject.assignee == authentication.name")
    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    @PreFilter("hasRole('MANAGER') or filterObject.assignee == authentication.name")
    public Iterable<Task> save(Iterable<Task> entities) {
        return taskRepository.saveAll(entities);
    }

}
