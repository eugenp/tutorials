package com.baeldung.architecture.application.task;

import com.baeldung.architecture.domain.task.Task;
import com.baeldung.architecture.domain.task.TaskService;
import com.baeldung.architecture.commands.task.*;

import org.springframework.stereotype.Component;

@Component
public class GetTasks implements GetAllTasks {

    private TaskService taskService;

    public GetTasks(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public Iterable<Task> getAll() {
        return taskService.getAllTasks();
    }
}
