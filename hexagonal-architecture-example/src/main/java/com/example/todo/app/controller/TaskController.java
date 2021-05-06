package com.example.todo.app.controller;

import com.example.todo.app.domain.Task;
import com.example.todo.app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> getAllTasks() {
        return new ResponseEntity<>(taskService.findAllTasks(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewTask(@RequestBody Task task) {
        taskService.addTask(task);
        return new ResponseEntity<>("New task successfully added", HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> removeTask(@PathVariable("id") int id) {
        if (taskService.removeTask(id))
            return new ResponseEntity<>("Task successfully removed", HttpStatus.OK);
        else
            return new ResponseEntity<>("No task found with the given id = " + id, HttpStatus.OK);
    }
}
