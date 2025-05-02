package com.baeldung.dapr.pubsub.publisher;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

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

import io.dapr.springboot.DaprAutoConfiguration;
import io.dapr.testcontainers.DaprContainer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(classes = { DaprPublisherTestApp.class, DaprTestContainersConfig.class, DaprAutoConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DaprPublisherIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(DaprPublisherIntegrationTest.class);
    private static final String READY_MESSAGE_PATTERN = ".*app is subscribed to the following topics.*";

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

        RestAssured.baseURI = "http://localhost:" + serverPort;
        Testcontainers.exposeHostPorts(serverPort);

        logger.info("[bael] waiting for ready...");
        Wait.forLogMessage(READY_MESSAGE_PATTERN, 1)
            .waitUntilReady(daprContainer);
        logger.info("[bael] ready.");
    }

    @Test
    void whenDriveUnacceptable_thenDrivesAcceptedIncrease() {
        int drivesAccepted = controller.getDrivesAccepted();

        given().contentType(ContentType.JSON)
            .body("""
                {
                "passengerId": "1",
                "location": "Point A",
                "destination": "%s Point B"
                }
                """.formatted(criteria))
            .when()
            .post("/passenger/request-ride")
            .then()
            .statusCode(200);

        await().atMost(Duration.ofSeconds(5))
            .until(controller::getDrivesAccepted, is(equalTo(drivesAccepted + 1)));
    }

    @Test
    void whenDriveAcceptable_thenDrivesRejectedIncrease() {
        int drivesRejected = controller.getDrivesRejected();

        given().contentType(ContentType.JSON)
            .body("""
                {
                "passengerId": "2",
                "location": "Point B",
                "destination": "West Side A"
                }
                """)
            .when()
            .post("/passenger/request-ride")
            .then()
            .statusCode(200);

        await().atMost(Duration.ofSeconds(5))
            .until(controller::getDrivesRejected, greaterThan(drivesRejected));
    }
}
