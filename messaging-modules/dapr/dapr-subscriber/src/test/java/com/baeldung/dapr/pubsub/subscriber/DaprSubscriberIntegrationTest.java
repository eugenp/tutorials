package com.baeldung.dapr.pubsub.subscriber;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.equalTo;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.wait.strategy.Wait;

import io.dapr.spring.messaging.DaprMessagingTemplate;
import io.dapr.springboot.DaprAutoConfiguration;
import io.dapr.testcontainers.DaprContainer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(classes = { DaprSubscriberTestApp.class, DaprTestContainersConfig.class, DaprSubscriberTestConfig.class, DaprAutoConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DaprSubscriberIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(DaprSubscriberIntegrationTest.class);
    private static final String SUBSCRIPTION_MESSAGE_PATTERN = ".*app is subscribed to the following topics.*";

    @Autowired
    private DaprMessagingTemplate<RideRequest> messaging;

    @Autowired
    private DriverRestController controller;

    @Autowired
    private DaprContainer daprContainer;

    @Value("${server.port}")
    public int serverPort;

    @BeforeEach
    void setUp() {
        logger.info("[bael] test setup");
        org.testcontainers.Testcontainers.exposeHostPorts(serverPort);

        RestAssured.baseURI = "http://localhost:" + serverPort;

        logger.info("[bael] waiting for ready...");
        Wait.forLogMessage(SUBSCRIPTION_MESSAGE_PATTERN, 1)
            .waitUntilReady(daprContainer);
        logger.info("[bael] ready.");
    }

    @Test
    void testMessageConsumer() {
        RideRequest ride = new RideRequest("abc-123", "Point A", "Point East Side B");
        messaging.send(DriverRestController.RIDE_REQUESTS_TOPIC, ride);

        given().contentType(ContentType.JSON)
            .when()
            .get("/driver/accepted-rides")
            .then()
            .statusCode(200);

        await().atMost(Duration.ofSeconds(5))
            .until(controller::getDrivesAccepted, equalTo(1));
    }
}
