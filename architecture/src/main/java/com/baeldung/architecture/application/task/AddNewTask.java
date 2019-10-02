package com.baeldung.architecture.application.task;

import com.baeldung.architecture.domain.task.Task;
import com.baeldung.architecture.domain.task.TaskService;
import com.baeldung.architecture.commands.task.*;

import org.springframework.stereotype.Component;

@Component
public class AddNewTask implements CreateTask {

    private TaskService taskService;

    public AddNewTask(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void create(Task newTask) {
        taskService.createTask(newTask);
    }
}
