package com.baeldung.architecture.application.task;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import com.baeldung.architecture.commands.task.CreateTask;
import com.baeldung.architecture.domain.task.Task;

import org.springframework.stereotype.Component;

@Component
public class AddNewDailyTask implements CreateTask {

    private AddNewTask addNewTask;

    public AddNewDailyTask(AddNewTask addNewTask) {
        this.addNewTask = addNewTask;
    }

    @Override
    public void create(Task newTask) {
        Instant initialDueDate = newTask.getDueDate();
        String description = newTask.getDescription();
        for (int i = 1; i <= 5; i++) {
            Task task = new Task(initialDueDate.plus(i, ChronoUnit.DAYS), description);
            addNewTask.create(task);
        }
    }
};
