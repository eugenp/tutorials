package com.baeldung.dapr.pubsub.publisher;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.wait.strategy.Wait;

import io.dapr.springboot.DaprAutoConfiguration;
import io.dapr.testcontainers.DaprContainer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(classes = { DaprPublisherTestApp.class, DaprTestContainersConfig.class, DaprAutoConfiguration.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DaprPublisherIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(DaprPublisherIntegrationTest.class);
    private static final String SUBSCRIPTION_MESSAGE_PATTERN = ".*app is subscribed to the following topics.*";

    @Autowired
    private TestSubscriberRestController controller;

    @Autowired
    private DaprContainer daprContainer;

    @Value("${server.port}")
    public int serverPort;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + serverPort;
        org.testcontainers.Testcontainers.exposeHostPorts(serverPort);

        logger.info("[bael] waiting for ready...");
        Wait.forLogMessage(SUBSCRIPTION_MESSAGE_PATTERN, 1)
            .waitUntilReady(daprContainer);
        logger.info("[bael] ready.");
    }

    @Test
    void testOrdersEndpointAndMessaging() {
        given().contentType(ContentType.JSON)
            .body("{ \"id\": \"abc-123\",\"item\": \"the mars volta LP\",\"amount\": 1}")
            .when()
            .post("/orders")
            .then()
            .statusCode(200);

        await().atMost(Duration.ofSeconds(15))
            .until(controller.getAllEvents()::size, equalTo(1));

        given().contentType(ContentType.JSON)
            .when()
            .get("/orders")
            .then()
            .statusCode(200)
            .body("size()", is(1));

        given().contentType(ContentType.JSON)
            .when()
            .queryParam("item", "the mars volta LP")
            .get("/orders/byItem/")
            .then()
            .statusCode(200)
            .body("size()", is(1));

        given().contentType(ContentType.JSON)
            .when()
            .queryParam("item", "other")
            .get("/orders/byItem/")
            .then()
            .statusCode(200)
            .body("size()", is(0));

        given().contentType(ContentType.JSON)
            .when()
            .queryParam("amount", 1)
            .get("/orders/byAmount/")
            .then()
            .statusCode(200)
            .body("size()", is(1));

        given().contentType(ContentType.JSON)
            .when()
            .queryParam("amount", 2)
            .get("/orders/byAmount/")
            .then()
            .statusCode(200)
            .body("size()", is(0));
    }
}
