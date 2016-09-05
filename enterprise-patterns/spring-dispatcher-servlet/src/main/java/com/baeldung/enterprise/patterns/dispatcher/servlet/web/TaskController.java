package com.baeldung.enterprise.patterns.dispatcher.servlet.web;

import com.baeldung.enterprise.patterns.dispatcher.servlet.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private Map<String, List<Task>> taskMap;

    @GetMapping("/{username}/list")
    public String listForm(
      Model model,
      @PathVariable("username") String username
    ) {
        List<Task> tasks = taskMap.get(username).stream()
          .sorted((t1, t2) -> t1.getDue().compareTo(t2.getDue()))
          .collect(Collectors.toList());
        model.addAttribute("username", username);
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @GetMapping("/{username}/add")
    public String addForm(
      Model model,
      @PathVariable("username") String username
    ) {
        model.addAttribute("username", username);
        model.addAttribute("task", new Task(new Date()));
        return "task-add";
    }

    @PostMapping("/{username}/add")
    public String addSubmit(
      @PathVariable("username") String username,
      @ModelAttribute Task task
    ) {
        List<Task> taskList = taskMap.get(username);
        if (taskList == null) {
            taskList = new ArrayList<>();
        }
        taskList.add(task);
        taskMap.put(username, taskList);
        return "redirect:list";
    }
}
