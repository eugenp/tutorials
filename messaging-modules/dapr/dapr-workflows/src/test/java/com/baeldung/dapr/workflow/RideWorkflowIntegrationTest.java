package com.baeldung.dapr.workflow;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.wait.strategy.Wait;

import com.baeldung.dapr.pubsub.model.RideRequest;
import com.baeldung.dapr.workflow.config.DaprWorkflowTestConfig;
import com.baeldung.dapr.workflow.model.RideWorkflowRequest;

import io.dapr.springboot.DaprAutoConfiguration;
import io.dapr.testcontainers.DaprContainer;
import io.dapr.workflows.client.DaprWorkflowClient;
import io.dapr.workflows.client.WorkflowInstanceStatus;
import io.dapr.workflows.client.WorkflowRuntimeStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(classes = { DaprWorkflowApp.class, DaprWorkflowTestConfig.class, DaprAutoConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RideWorkflowIntegrationTest {

    @Autowired
    private DaprContainer daprContainer;

    @Autowired
    private DaprWorkflowClient workflowClient;

    @Value("${server.port}")
    private int serverPort;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + serverPort;
        org.testcontainers.Testcontainers.exposeHostPorts(serverPort);

        Wait.forLogMessage(".*app is subscribed to the following topics.*", 1)
            .waitUntilReady(daprContainer);
    }

    @Test
    void whenWorkflowStartedViaRest_thenAllActivitiesExecute() {
        RideRequest rideRequest = new RideRequest("passenger-1", "Downtown", "Airport");
        RideWorkflowRequest workflowRequest = new RideWorkflowRequest("ride-123", rideRequest, "driver-456", null);

        // Start the workflow via REST
        RideWorkflowRequest response = given().contentType(ContentType.JSON)
            .body(workflowRequest)
            .when()
            .post("/workflow/start-ride")
            .then()
            .statusCode(200)
            .extract()
            .as(RideWorkflowRequest.class);

        String instanceId = response.getWorkflowInstanceId();
        assertNotNull(instanceId);

        // Wait until the workflow is running (it will wait for the external event)
        await().atMost(Duration.ofSeconds(10))
            .pollInterval(Duration.ofMillis(200))
            .until(() -> {
                WorkflowInstanceStatus status = workflowClient.getInstanceState(instanceId, false);
                return status != null && status.getRuntimeStatus() == WorkflowRuntimeStatus.RUNNING;
            });

        // Raise the passenger confirmation event so the workflow can complete
        given().contentType(ContentType.TEXT)
            .body("confirmed")
            .when()
            .post("/workflow/confirm/" + instanceId)
            .then()
            .statusCode(200);

        // Wait for workflow to complete
        await().atMost(Duration.ofSeconds(15))
            .pollInterval(Duration.ofMillis(500))
            .until(() -> {
                WorkflowInstanceStatus status = workflowClient.getInstanceState(instanceId, false);
                return status != null && status.getRuntimeStatus() == WorkflowRuntimeStatus.COMPLETED;
            });

        // Verify the workflow completed successfully
        WorkflowInstanceStatus finalStatus = workflowClient.getInstanceState(instanceId, true);
        assertEquals(WorkflowRuntimeStatus.COMPLETED, finalStatus.getRuntimeStatus());
    }

    @Test
    void whenWorkflowStartedViaClient_thenAllActivitiesExecute() {
        RideRequest rideRequest = new RideRequest("passenger-2", "Uptown", "Station");
        RideWorkflowRequest workflowRequest = new RideWorkflowRequest("ride-456", rideRequest, "driver-789", null);

        // Start the workflow via the workflow client
        String instanceId = workflowClient.scheduleNewWorkflow(RideProcessingWorkflow.class, workflowRequest);
        assertNotNull(instanceId);

        // Wait until the workflow is running (it will wait for the external event)
        await().atMost(Duration.ofSeconds(10))
            .pollInterval(Duration.ofMillis(200))
            .until(() -> {
                WorkflowInstanceStatus status = workflowClient.getInstanceState(instanceId, false);
                return status != null && status.getRuntimeStatus() == WorkflowRuntimeStatus.RUNNING;
            });

        // Raise the passenger confirmation event directly via the workflow client
        workflowClient.raiseEvent(instanceId, "passenger-confirmation", "confirmed");

        // Wait for workflow to complete
        await().atMost(Duration.ofSeconds(15))
            .pollInterval(Duration.ofMillis(500))
            .until(() -> {
                WorkflowInstanceStatus status = workflowClient.getInstanceState(instanceId, false);
                return status != null && status.getRuntimeStatus() == WorkflowRuntimeStatus.COMPLETED;
            });

        // Verify the workflow completed successfully
        WorkflowInstanceStatus finalStatus = workflowClient.getInstanceState(instanceId, true);
        assertEquals(WorkflowRuntimeStatus.COMPLETED, finalStatus.getRuntimeStatus());
    }

    @Test
    void whenActivityFails_thenRetryPolicyApplies() {
        // Create a request that will cause validation to fail initially
        RideWorkflowRequest invalidRequest = new RideWorkflowRequest("ride-789", new RideRequest("passenger-3", "Park", "Beach"), "", null);

        String instanceId = workflowClient.scheduleNewWorkflow(RideProcessingWorkflow.class, invalidRequest);

        // Wait and verify the workflow eventually fails after retries
        await().atMost(Duration.ofSeconds(20))
            .pollInterval(Duration.ofMillis(500))
            .until(() -> {
                WorkflowInstanceStatus status = workflowClient.getInstanceState(instanceId, false);
                return status != null && status.getRuntimeStatus() == WorkflowRuntimeStatus.FAILED;
            });

        WorkflowInstanceStatus finalStatus = workflowClient.getInstanceState(instanceId, true);
        assertEquals(WorkflowRuntimeStatus.FAILED, finalStatus.getRuntimeStatus());
    }
}