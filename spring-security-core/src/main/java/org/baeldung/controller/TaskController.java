package org.baeldung.controller;

import org.baeldung.entity.Task;
import org.baeldung.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Task>> findAllTasks() {
        Iterable<Task> tasks = taskService.findAll();

        return ResponseEntity.ok().body(tasks);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Iterable<Task>> addTasks(@RequestBody Iterable<Task> newTasks) {
        Iterable<Task> tasks = taskService.save(newTasks);

        return ResponseEntity.ok().body(tasks);
    }
}
