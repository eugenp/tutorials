package com.baeldung.temporal;

import com.baeldung.temporal.workflows.hello.HelloWorkflow;
import com.baeldung.temporal.workflows.hello.HelloWorker;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        client = testEnv.getWorkflowClient();
    }

    @AfterEach
    public void stopWorker() {
        testEnv.close();
    }

    @Test
    void givenPerson_whenSayHello_thenSuccess() throws Exception {

        var sayHelloWorker = new HelloWorker();
        sayHelloWorker.init(worker);

        // We must register all activities/worklows before starting the test environment
        testEnv.start();

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

        // Create a blocking workflow using tbe execution's workflow id
        var syncWorkflow = client.newWorkflowStub(HelloWorkflow.class,execution.getWorkflowId());


        // The sync workflow stub will block until it completes. Notice that the call argumento here is ignored!
        assertEquals("Hello, Baeldung", syncWorkflow.hello("ignored"));
    }
}