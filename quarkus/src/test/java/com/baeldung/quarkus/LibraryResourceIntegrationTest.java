package com.baeldung.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;

@QuarkusTest
class LibraryResourceIntegrationTest {

    @Test
    void whenGetBooksByTitle_thenBookShouldBeFound() {
        given().contentType(ContentType.JSON).param("query", "Dune")
          .when().get("/library/book")
          .then().statusCode(200)
          .body("size()", is(1))
          .body("title", hasItem("Dune"))
          .body("author", hasItem("Frank Herbert"));
    }

}
