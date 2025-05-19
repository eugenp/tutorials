package com.baeldung.dapr.pubsub.subscriber;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.wait.strategy.Wait;

import com.baeldung.dapr.pubsub.model.RideRequest;

import io.dapr.spring.messaging.DaprMessagingTemplate;
import io.dapr.springboot.DaprAutoConfiguration;
import io.dapr.testcontainers.DaprContainer;

@SpringBootTest(classes = { DaprSubscriberTestApp.class, DaprTestContainersConfig.class, DaprMessagingConfig.class, DaprAutoConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DaprSubscriberIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(DaprSubscriberIntegrationTest.class);
    private static final String READY_MESSAGE_PATTERN = ".*app is subscribed to the following topics.*";

    @Autowired
    DaprMessagingTemplate<RideRequest> messaging;

    @Autowired
    DriverRestController controller;

    @Autowired
    DaprContainer daprContainer;

    @Value("${server.port}")
    int serverPort;

    @Value("${driver.acceptance.criteria}")
    String criteria;

    @BeforeEach
    void setUp() {
        logger.info("[bael] test setup");
        Testcontainers.exposeHostPorts(serverPort);

        logger.info("[bael] waiting for ready...");
        Wait.forLogMessage(READY_MESSAGE_PATTERN, 1)
            .waitUntilReady(daprContainer);
        logger.info("[bael] ready.");
    }

    @Test
    void whenDriveAcceptable_thenDrivesAcceptedIncrease() {
        int drivesAccepted = controller.getDrivesAccepted();

        RideRequest ride = new RideRequest("1", "Point A", String.format("%s Point B", criteria));
        messaging.send(DriverRestController.RIDE_REQUESTS_TOPIC, ride);

        await().atMost(Duration.ofSeconds(5))
            .until(controller::getDrivesAccepted, equalTo(drivesAccepted + 1));
    }

    @Test
    void whenDriveUnacceptable_thenDrivesRejectedIncrease() {
        int drivesRejected = controller.getDrivesRejected();

        RideRequest request = new RideRequest("2", "Point B", "West Side Point A");
        messaging.send(DriverRestController.RIDE_REQUESTS_TOPIC, request);

        await().atMost(Duration.ofSeconds(5))
            .until(controller::getDrivesRejected, greaterThan(drivesRejected));
    }
}
