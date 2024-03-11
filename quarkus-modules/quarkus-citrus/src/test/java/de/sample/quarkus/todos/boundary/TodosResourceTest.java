package de.sample.quarkus.todos.boundary;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import de.samples.quarkus.todos.boundary.TodosResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest // @ApplicationPath not supported
@TestHTTPEndpoint(TodosResource.class)
class TodosResourceTest {

    @Test
    void testHelloEndpoint() {
        given()
          .when()
          	.get()
          .then()
             .statusCode(200);
    }

}