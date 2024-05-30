package com.baeldung.quarkus.todos;

import com.baeldung.quarkus.todos.boundary.TodosResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
@TestHTTPEndpoint(TodosResource.class)
public class TodosServiceTests {

    /*
     * This is a test without Citrus. We can test the API to return
     * the expected response, but how do we check for the message to
     * be sent to Kafka?
     * (👉https://www.infoq.com/articles/testing-quarkus-reactive/)
     */

    @Test
    void shouldProduceMessageToKafkaOnCreateTodo() {
        given()
                .body("{\"title\": \"test\"}")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("id", notNullValue());
    }
}
