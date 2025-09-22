package com.baeldung.temporal;

import com.baeldung.temporal.workflows.hello.HelloWorkflow;
import com.baeldung.temporal.workflows.hello.HelloWorkflowRegistrar;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;
import io.temporal.workflow.Workflow;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloWorkerUnitTest {
    private final Logger log = LoggerFactory.getLogger(HelloWorkerUnitTest.class);
    private static final String QUEUE_NAME = "say-hello-queue";


    private TestWorkflowEnvironment testEnv;
    private Worker worker;
    private WorkflowClient client;


    @BeforeEach
    public void startWorker() {

        log.info("Creating test environment...");
        testEnv = TestWorkflowEnvironment.newInstance();
        worker = testEnv.newWorker(QUEUE_NAME);
        HelloWorkflowRegistrar.newInstance().register(worker);
        client = testEnv.getWorkflowClient();

//        var serviceStubs = WorkflowServiceStubs.newServiceStubs(WorkflowServiceStubsOptions
//          .newBuilder()
//            .setTarget("localhost:7233")
//            .setEnableKeepAlive(true)
//            .setEnableHttps(false)
//          .build());
//
//        client = WorkflowClient.newInstance(serviceStubs);

        testEnv.start();
    }

    @AfterEach
    public void stopWorker() {
        testEnv.close();
    }

    @Test
    void givenPerson_whenSayHelloAsync_thenSuccess() throws Exception {

        // Create workflow stub wich allow us to create workflow instances
        var wfid = UUID.randomUUID().toString();
        var workflow = client.newWorkflowStub(
          HelloWorkflow.class,
          WorkflowOptions.newBuilder()
            .setTaskQueue(QUEUE_NAME)
            .setWorkflowId(wfid)
            .build()
        );

        // Invoke workflow asynchronously.
        var execution = WorkflowClient.start(workflow::hello,"Baeldung");
        var workflowStub = client.newUntypedWorkflowStub(execution.getWorkflowId());

        // Retrieve a CompletableFuture we can use to wait for the result.
        var future = workflowStub.getResultAsync(String.class);
        log.info("Waiting for workflow to complete...");
        var result = future.get();
        log.info("Workflow completed with result: {}", result);
        assertEquals("Hello, Baeldung", result);
    }

    @Test
    void givenPerson_whenSayHelloSync_thenSuccess() throws Exception {

        // Create workflow stub wich allow us to create workflow instances
        var wfid = UUID.randomUUID().toString();
        var workflow = client.newWorkflowStub(
          HelloWorkflow.class,
          WorkflowOptions.newBuilder()
            .setTaskQueue(QUEUE_NAME)
            .setWorkflowId(wfid)
            .build()
        );

        // Invoke workflow synchronously.
        var result = workflow.hello("Baeldung");

        // The sync workflow stub will block until it completes. Notice that the call argumento here is ignored!
        assertEquals("Hello, Baeldung", result);
    }
}