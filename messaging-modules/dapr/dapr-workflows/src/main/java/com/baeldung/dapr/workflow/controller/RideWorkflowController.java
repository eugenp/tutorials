package com.baeldung.dapr.workflow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.dapr.workflow.RideProcessingWorkflow;
import com.baeldung.dapr.workflow.model.RideWorkflowRequest;

import io.dapr.workflows.client.DaprWorkflowClient;
import io.dapr.workflows.client.WorkflowInstanceStatus;

@RestController
@RequestMapping("/workflow")
public class RideWorkflowController {

    private static final Logger logger = LoggerFactory.getLogger(RideWorkflowController.class);

    private final DaprWorkflowClient workflowClient;

    public RideWorkflowController(DaprWorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @PostMapping("/start-ride")
    public RideWorkflowRequest startRideWorkflow(@RequestBody RideWorkflowRequest request) {
        logger.info("Starting workflow for ride: {}", request.getRideId());

        String instanceId = workflowClient.scheduleNewWorkflow(RideProcessingWorkflow.class, request);

        request.setWorkflowInstanceId(instanceId);
        logger.info("Workflow started with instance ID: {}", instanceId);

        return request;
    }

    @GetMapping("/status/{instanceId}")
    public WorkflowInstanceStatus getWorkflowStatus(@PathVariable String instanceId) {
        return workflowClient.getInstanceState(instanceId, true);
    }

    @PostMapping("/confirm/{instanceId}")
    public void confirmRide(@PathVariable("instanceId") String instanceId, @RequestBody String confirmation) {
        logger.info("Raising confirmation event for workflow: {}", instanceId);
        workflowClient.raiseEvent(instanceId, "passenger-confirmation", confirmation);
        logger.info("Confirmation event raised: {}", confirmation);
    }
}
