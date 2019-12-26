package com.baeldung.boot.problem.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.boot.problem.dto.Task;
import com.baeldung.boot.problem.problems.TaskNotFoundProblem;

@RestController
@RequestMapping("/tasks")
public class ProblemDemoController {

    private static final Map<Long, Task> MY_TASKS;

    static {
        MY_TASKS = new HashMap<>();
        MY_TASKS.put(1L, new Task(1L, "My first task"));
        MY_TASKS.put(2L, new Task(2L, "My second task"));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getTasks() {
        return new ArrayList<>(MY_TASKS.values());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Task getTasks(@PathVariable("id") Long taskId) {
        if (MY_TASKS.containsKey(taskId)) {
            return MY_TASKS.get(taskId);
        } else {
            throw new TaskNotFoundProblem(taskId);
        }
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable("id") Long id) {
        throw new UnsupportedOperationException();
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long id) {
        throw new AccessDeniedException("You can't delete this task");
    }

}
