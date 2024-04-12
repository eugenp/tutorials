package com.baeldung.app.controller;

import com.baeldung.app.entity.Task;
import com.baeldung.app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    @GetMapping
    public ResponseEntity<Iterable<Task>> findAllTasks() {
        Iterable<Task> tasks = taskService.findAll();

        return ResponseEntity.ok().body(tasks);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Iterable<Task>> addTasks(@RequestBody Iterable<Task> newTasks) {
        Iterable<Task> tasks = taskService.save(newTasks);

        return ResponseEntity.ok().body(tasks);
    }
}
