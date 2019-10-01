package com.baeldung.architecture.framework.http.task;

import java.time.Instant;

import com.baeldung.architecture.application.task.AddNewTask;
import com.baeldung.architecture.application.task.GetTasks;
import com.baeldung.architecture.domain.task.Task;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("task")
public class TaskApiController {

    private AddNewTask addNewTask;
    private GetTasks getTasks;

    public TaskApiController(
        AddNewTask addNewTask,
        GetTasks getTasks
    ) {
        this.addNewTask = addNewTask;
        this.getTasks = getTasks;
    }

    @GetMapping
    Iterable<Task> listTasks() {
        return getTasks.getAll();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    void createTask(@RequestBody TaskRequest taskRequest) {
        Task task = new Task();
        task.setDescription(taskRequest.getDescription());
        task.setDueDate(Instant.parse(taskRequest.getDueDate()));
        addNewTask.create(task);
    }
}