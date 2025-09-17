package com.baeldung.temporal;

import com.baeldung.temporal.workflows.hello.HelloWorkflow;
import com.baeldung.temporal.workflows.hello.HelloWorker;
import com.baeldung.temporal.workflows.hellov2.HelloV2Worker;
import com.baeldung.temporal.workflows.hellov2.HelloWorkflowV2;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.VersioningBehavior;
import io.temporal.common.WorkerDeploymentVersion;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.*;
import io.temporal.workflow.Workflow;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Hello2WorkerIntegrationTest {
    private static final String QUEUE_NAME = "say-hello-queue";
    private static final Logger log = LoggerFactory.getLogger(Hello2WorkerIntegrationTest.class);

    private WorkerFactory factory;

    @BeforeEach
    public void startWorker() {

        log.info("Creating worker...");
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        this.factory = WorkerFactory.newInstance(client);

        var workerOptions = WorkerOptions.newBuilder()
          .setUsingVirtualThreads(true)
          .build();
        Worker worker = factory.newWorker(QUEUE_NAME,workerOptions);

        var helloV2Worker = new HelloV2Worker();
        helloV2Worker.init(worker);

        log.info("Starting worker...");
        factory.start();
        log.info("Worker started.");
    }

    @AfterEach
    public void stopWorker() {
        log.info("Stopping worker...");
        factory.shutdown();
        log.info("Worker stopped.");
    }

    @Test
    void givenPerson_whenSayHello_thenSuccess() {

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);

        var wfid = UUID.randomUUID().toString();

        var workflow = client.newWorkflowStub(
          HelloWorkflowV2.class,
          WorkflowOptions.newBuilder()
            .setTaskQueue(QUEUE_NAME)
            .setWorkflowId(wfid)
            .build()
        );


        // Invoke workflow asynchronously.
        var execution = WorkflowClient.start(workflow::hello,"Baeldung");
        log.info("Workflow started: id={}, runId={}",
          execution.getWorkflowId(),
          execution.getRunId());

        // Create a blocking workflow using the execution's workflow id
        var syncWorkflow = client.newWorkflowStub(HelloWorkflowV2.class,execution.getWorkflowId());

        // The sync workflow stub will block until it completes. Notice that the call argument here is ignored!
        assertEquals("Workflow OK", syncWorkflow.hello("ignored"));

    }



}