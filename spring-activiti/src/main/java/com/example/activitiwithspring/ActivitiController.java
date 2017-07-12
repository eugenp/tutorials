package com.example.activitiwithspring;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActivitiController {
    private static final Logger logger = LoggerFactory.getLogger(ActivitiController.class);
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/start-process")
    public String startProcess() {
        runtimeService.startProcessInstanceByKey("my-process");
        return "Process started. Number of currently running process instances = " + runtimeService.createProcessInstanceQuery()
                .count();
    }

    @GetMapping("/get-tasks/{processInstanceId}")
    public List<TaskRepresentation> getTasks(@PathVariable String processInstanceId) {
        List<Task> usertasks = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .list();

        List<TaskRepresentation> tasks =  usertasks.stream().map(task -> {
            TaskRepresentation taskRepresentation = new TaskRepresentation(task.getId(), task.getName(), task.getProcessInstanceId());
            return taskRepresentation;
        }).collect(Collectors.toList());
        return tasks;
    }

    @GetMapping("/complete-task-A/{processInstanceId}")
    public TaskRepresentation completeTaskA(@PathVariable String processInstanceId) {
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        taskService.complete(task.getId());
        logger.info("Task completed");
        task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        return new TaskRepresentation(task.getId(), task.getName(), task.getProcessInstanceId());
    }
}
