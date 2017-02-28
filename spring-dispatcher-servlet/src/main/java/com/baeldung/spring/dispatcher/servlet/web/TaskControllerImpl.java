package com.baeldung.spring.dispatcher.servlet.web;

import com.baeldung.spring.dispatcher.servlet.models.Attachment;
import com.baeldung.spring.dispatcher.servlet.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TaskControllerImpl implements TaskController {
    @Autowired
    private Map<String, List<Task>> taskMap;

    @Override
    public String listTasks(
      @PathVariable("username") String username,
      Model model
    ) {
        List<Task> tasks = taskMap.get(username).stream()
          .sorted(Comparator.comparing(Task::getDue))
          .collect(Collectors.toList());
        model.addAttribute("username", username);
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    @Override
    public String addTask(
      @PathVariable("username") String username,
      Model model
    ) {
        model.addAttribute("username", username);
        model.addAttribute("task", new Task(new Date()));
        return "task-add";
    }

    @Override
    public String addTask(
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

    @Override
    public String getTask(
      @PathVariable("username") String username,
      @PathVariable("id") int id,
      Model model
    ) {
        Task task = taskMap.get(username).get(id);
        model.addAttribute("username", username);
        model.addAttribute("id", id);
        model.addAttribute("task", task);
        return "task-get";
    }

    @Override
    public String attachToTask(
      @PathVariable("username") String username,
      @PathVariable("id") int id,
      Model model
    ) {
        model.addAttribute("username", username);
        model.addAttribute("id", id);
        return "task-attach";
    }

    @Override
    public String attachToTask(
      @PathVariable("username") String username,
      @PathVariable("id") int id,
      @RequestParam("attachment") MultipartFile multipartFile,
      @RequestParam("description") String description
    ) {
        Task task = taskMap.get(username).get(id);
        Attachment attachment = new Attachment(multipartFile.getOriginalFilename(),
          description);
        task.getAttachments().add(attachment);
        try (InputStream inputStream =
               new BufferedInputStream(multipartFile.getInputStream());
             OutputStream outputStream =
               new BufferedOutputStream(Files.newOutputStream(
                 Paths.get("/tmp", attachment.getId())))) {
            byte[] buf = new byte[1024 * 16];
            int len;
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file!", e);
        }
        return "redirect:./";
    }
}
