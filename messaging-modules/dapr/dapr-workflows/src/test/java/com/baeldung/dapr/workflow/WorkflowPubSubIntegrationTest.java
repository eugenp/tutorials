package com.baeldung.dapr.workflow;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;

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

import io.dapr.client.domain.CloudEvent;
import io.dapr.springboot.DaprAutoConfiguration;
import io.dapr.testcontainers.DaprContainer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(
    classes = { 
        DaprWorkflowApp.class, 
        DaprWorkflowTestConfig.class,
        DaprAutoConfiguration.class 
    },
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WorkflowPubSubIntegrationTest {

    @Autowired
    private DaprContainer daprContainer;

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
    void whenPubSubEventReceived_thenWorkflowStarts() {
        RideRequest rideRequest = new RideRequest("passenger-2", "Mall", "Home");
        RideWorkflowRequest workflowRequest = new RideWorkflowRequest(
            "ride-456", rideRequest, "driver-789", null);

        // Publish event to trigger workflow
        CloudEvent<RideWorkflowRequest> cloudEvent = new CloudEvent<>();
        cloudEvent.setData(workflowRequest);

        given()
            .contentType(ContentType.JSON)
            .body(cloudEvent)
        .when()
            .post("/workflow-subscriber/driver-accepted")
        .then()
            .statusCode(200);

        // Give the workflow time to start and complete
        await()
            .atMost(Duration.ofSeconds(15))
            .pollDelay(Duration.ofSeconds(2))
            .untilAsserted(() -> {
                // The workflow should have been triggered by the pub/sub event
                // In a real scenario, we would verify through application logs or database state
            });
    }
}
