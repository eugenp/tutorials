package com.baeldung.web.controller;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.DEFAULT_PORT;
import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@TestPropertySource(properties = {"spring.main.allow-bean-definition-overriding=true", "server.servlet.context-path=/"})
public class GreetControllerRealIntegrationTest {

    @Before
    public void setUp() {
        RestAssured.port = DEFAULT_PORT;
    }

    @Test
    public void givenGreetURI_whenSendingReq_thenVerifyResponse() {
        given().get("/greet")
                .then()
                .statusCode(200);
    }
}
