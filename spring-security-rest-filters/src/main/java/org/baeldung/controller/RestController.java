package org.baeldung.controller;

import org.baeldung.entity.Task;
import org.baeldung.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author felipereis
 *
 */
@Controller
@RequestMapping("api")
public class RestController {

    @Autowired
    private TaskRepository taskRepository;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Task>> findAllTasks() {
        Iterable<Task> tasks = taskRepository.findAll();

        return ResponseEntity.ok().body(tasks);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Iterable<Task>> addTasks(@RequestBody Iterable<Task> newTasks) {
        Iterable<Task> tasks = taskRepository.save(newTasks);

        return ResponseEntity.ok().body(tasks);
    }
}
