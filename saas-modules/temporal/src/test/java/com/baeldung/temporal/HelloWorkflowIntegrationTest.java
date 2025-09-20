package com.baeldung.temporal;

import com.baeldung.temporal.workflows.hello.HelloWorkflow;
import com.baeldung.temporal.workflows.hello.HelloWorkflowRegistrar;
import com.baeldung.temporal.workflows.hellov2.HelloWorkflowV2;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import io.temporal.worker.WorkerOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloWorkflowIntegrationTest {
    private static final String QUEUE_NAME = "say-hello-queue";
    private static final Logger log = LoggerFactory.getLogger(HelloWorkflowIntegrationTest.class);

    private WorkerFactory factory;

    @BeforeEach
    public void startWorker() {

        log.info("Creating worker...");
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        this.factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker(QUEUE_NAME);

        HelloWorkflowRegistrar.newInstance().register(worker);

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
          HelloWorkflow.class,
          WorkflowOptions.newBuilder()
            .setTaskQueue(QUEUE_NAME)
            .setWorkflowId(wfid)
            .build()
        );

        // Run the workflow synchronously
        var result = workflow.hello("Baeldung");
        assertEquals("Hello, Baeldung", result);

    }



}