package com.baeldung.activitiwithspring;


import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ProcessExecutionIntegrationTest {

    @Test
    public void givenBPMN_whenDeployProcess_thenDeployed() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
          .addClasspathResource("org/activiti/test/vacationRequest.bpmn20.xml")
          .deploy();
        Long count = repositoryService.createProcessDefinitionQuery().count();
        assertTrue(count >= 1);
    }

    @Test
    public void givenProcessDefinition_whenStartProcessInstance_thenProcessRunning() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
          .addClasspathResource("org/activiti/test/vacationRequest.bpmn20.xml")
          .deploy();

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService
          .startProcessInstanceByKey("vacationRequest", variables);

        Long count = runtimeService.createProcessInstanceQuery().count();
        assertTrue(count >= 1);
    }

    @Test
    public void givenProcessInstance_whenCompleteTask_thenProcessExecutionContinues() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
          .addClasspathResource("org/activiti/test/vacationRequest.bpmn20.xml")
          .deploy();

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");

        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService
          .startProcessInstanceByKey("vacationRequest", variables);

        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();

        Task task = tasks.get(0);

        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put("vacationApproved", "false");
        taskVariables.put("comments", "We have a tight deadline!");
        taskService.complete(task.getId(), taskVariables);

        Task currentTask = taskService.createTaskQuery().taskName("Modify vacation request").singleResult();
        assertNotNull(currentTask);
    }

    @Test(expected = ActivitiException.class)
    public void givenProcessDefinition_whenSuspend_thenNoProcessInstanceCreated() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.createDeployment()
          .addClasspathResource("org/activiti/test/vacationRequest.bpmn20.xml")
          .deploy();

        RuntimeService runtimeService = processEngine.getRuntimeService();
        repositoryService.suspendProcessDefinitionByKey("vacationRequest");
        runtimeService.startProcessInstanceByKey("vacationRequest");
    }
}
