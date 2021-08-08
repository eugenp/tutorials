package com.baeldung.hexagonal_example.infrastructure.adapters.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpContactApiPortImplIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_whenCreatingAValidContact_shouldReturnId() throws Exception {
        String name = "James Santos";
        ContactRequestVM request = new ContactRequestVM(
                name,
          "test@test.com",
          "+1(988)455-8585"
        );

        given()
            .contentType(ContentType.JSON)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .port(port)
            .post("/contact")
        .then()
            .statusCode(OK.value())
            .body("id", equalTo(name));
    }

    @Test
    void create_whenCreatingAInvalidContact_shouldReturn400() throws Exception {
        String name = "James Santos";
        ContactRequestVM request = new ContactRequestVM(
                name,
                "test.test.com",
                "+1(988)455-8585"
        );

        given()
            .contentType(ContentType.JSON)
            .body(objectMapper.writeValueAsString(request))
        .when()
            .port(port)
            .post("/contact")
        .then()
            .statusCode(BAD_REQUEST.value())
            .body("errorMessage", equalTo("Invalid email"));
    }
}