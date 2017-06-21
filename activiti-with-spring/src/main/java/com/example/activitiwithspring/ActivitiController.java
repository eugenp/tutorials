package com.example.activitiwithspring;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/start-process")
    public String startProcess() {
        runtimeService.startProcessInstanceByKey("my-process");
        return "Process started. Number of currently running process instances = " + runtimeService.createProcessInstanceQuery()
            .count();
    }

    @RequestMapping(value = "/complete-task-A/{processInstanceId}")
    public String completeTaskA(@PathVariable String processInstanceId) {
        Task task = taskService.createTaskQuery()
            .processInstanceId(processInstanceId)
            .singleResult();
        taskService.complete(task.getId());
        logger.info("Task completed");
        Task tasksAtB = taskService.createTaskQuery()
            .processInstanceId(processInstanceId)
            .singleResult();
        logger.info("Returning the next task of the process: " + tasksAtB.getId());

        return tasksAtB.getName();
    }
}
