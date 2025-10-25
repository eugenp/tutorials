package com.baeldung.dapr.workflow.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.dapr.workflow.RideProcessingWorkflow;
import com.baeldung.dapr.workflow.model.RideWorkflowRequest;

import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import io.dapr.workflows.client.DaprWorkflowClient;

@RestController
@RequestMapping("/workflow-subscriber")
public class WorkflowEventSubscriber {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowEventSubscriber.class);
    public static final String DRIVER_ACCEPTANCE_TOPIC = "driver-acceptance";

    @Autowired
    private DaprWorkflowClient workflowClient;

    @PostMapping("/driver-accepted")
    @Topic(pubsubName = "ride-hailing", name = DRIVER_ACCEPTANCE_TOPIC)
    public void onDriverAcceptance(@RequestBody CloudEvent<RideWorkflowRequest> event) {
        RideWorkflowRequest request = event.getData();
        logger.info("Received driver acceptance event for ride: {}", request.getRideId());

        // Start the ride processing workflow
        String instanceId = workflowClient.scheduleNewWorkflow(
            RideProcessingWorkflow.class, request);

        logger.info("Started workflow {} for accepted ride {}", instanceId, request.getRideId());
    }
}
