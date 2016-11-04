package com.baeldung.spring.dispatcher.servlet.web;

import com.baeldung.spring.dispatcher.servlet.models.Task;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/tasks")
public interface TaskController {
    @GetMapping("/{username}/list")
    String listTasks(
      @PathVariable("username") String username,
      Model model
    );

    @GetMapping("/{username}/add")
    String addTask(
      @PathVariable("username") String username,
      Model model
    );

    @PostMapping("/{username}/add")
    String addTask(
      @PathVariable("username") String username,
      @ModelAttribute Task task
    );

    @GetMapping("/{username}/get/{id}")
    String getTask(
      @PathVariable("username") String username,
      @PathVariable("id") int id,
      Model model
    );

    @GetMapping("/{username}/get/{id}/attach")
    String attachToTask(
      @PathVariable("username") String username,
      @PathVariable("id") int id,
      Model model
    );

    @PostMapping("/{username}/get/{id}/attach")
    String attachToTask(
      @PathVariable("username") String username,
      @PathVariable("id") int id,
      @RequestParam("attachment") MultipartFile attachment,
      @RequestParam("description") String description
    );
}
