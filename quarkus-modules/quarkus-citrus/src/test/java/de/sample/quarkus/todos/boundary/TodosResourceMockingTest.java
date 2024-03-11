package de.sample.quarkus.todos.boundary;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

import java.util.Optional;

import io.quarkus.test.InjectMock;
import org.junit.jupiter.api.Test;

import de.samples.quarkus.todos.boundary.TodosResource;
import de.samples.quarkus.todos.domain.TodosService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@TestHTTPEndpoint(TodosResource.class)
public class TodosResourceMockingTest {

	// use Mockito
	@InjectMock
	TodosService service;
	
    @Test
    public void testHelloEndpoint() {
    	when(service.findById(1L)).thenReturn(Optional.empty());
        given()
          .when()
          	.get("/1")
          .then()
             .statusCode(404);
    }

}