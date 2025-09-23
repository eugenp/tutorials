package com.baeldung.temporal.workflows.hello;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorkflowApplication {

    private static final String QUEUE_NAME = "say-hello-queue";
    private static final Logger log = LoggerFactory.getLogger(HelloWorkflowApplication.class);

    public static void main(String[] args) {

        log.info("Creating worker...");
        var service = WorkflowServiceStubs.newLocalServiceStubs();
        var client = WorkflowClient.newInstance(service);
        var factory = WorkerFactory.newInstance(client);
        var worker = factory.newWorker(QUEUE_NAME);

        log.info("Registering workflows and activities...");
        HelloWorkflowRegistrar.newInstance().register(worker);

        log.info("Starting worker...");
        factory.start();
    }
}
