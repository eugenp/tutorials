package com.baeldung.activiti.security.config;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    SpringProcessEngineConfiguration config;

    @GetMapping("/protected-process")
    public String startProcess() {

        String userId = SecurityContextHolder.getContext()
            .getAuthentication()
            .getName();

        identityService.setAuthenticatedUserId(userId);

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("protected-process");

        List<Task> usertasks = taskService.createTaskQuery()
            .processInstanceId(pi.getId())
            .list();

        taskService.complete(usertasks.iterator()
            .next()
            .getId());

        return "Process started. Number of currently running process instances = " + runtimeService.createProcessInstanceQuery()
            .count();
    }

}
