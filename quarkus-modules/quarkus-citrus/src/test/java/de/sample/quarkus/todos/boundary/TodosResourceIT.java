package de.sample.quarkus.todos.boundary;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import de.samples.quarkus.todos.boundary.TodosResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest // invoked after JAR packaging
@TestHTTPEndpoint(TodosResource.class)
class TodosResourceIT {

	// mocking is not possible
	// running from IDE is not possible
	// run with "mvn verify"
	
    @Test
    void testHelloEndpoint() {
        given()
          .when()
          	.get()
          .then()
             .statusCode(200);
    }

}