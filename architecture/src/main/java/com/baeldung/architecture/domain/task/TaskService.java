package com.baeldung.architecture.domain.task;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void createTask(Task task) {
        taskRepository.save(task);
    }

    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

}
