package com.baeldung.quarkus;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.net.URL;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;

@QuarkusTest
class LibraryResourceIntegrationTest {

    @TestHTTPEndpoint(LibraryResource.class)
    @TestHTTPResource("book")
    URL libraryEndpoint;

    @Test
    void testGetBookEndpoint() {
        given().contentType(ContentType.JSON).param("query", "Dune")
          .when().get(libraryEndpoint)
          .then().statusCode(200)
          .body("size()", is(1))
          .body("title", hasItem("Dune"))
          .body("author", hasItem("Frank Herbert"));
    }

}
