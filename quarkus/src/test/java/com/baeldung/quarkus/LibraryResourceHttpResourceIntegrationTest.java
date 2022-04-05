package com.baeldung.quarkus;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static io.restassured.RestAssured.given;
import static java.nio.charset.Charset.defaultCharset;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class LibraryResourceHttpResourceIntegrationTest {

    @TestHTTPEndpoint(LibraryResource.class)
    @TestHTTPResource("book")
    URL libraryEndpoint;

    @Test
    void whenGetBooksByTitle_thenBookShouldBeFound() {
        given().contentType(ContentType.JSON).param("query", "Dune")
          .when().get(libraryEndpoint)
          .then().statusCode(200)
          .body("size()", is(1))
          .body("title", hasItem("Dune"))
          .body("author", hasItem("Frank Herbert"));
    }

    @Test
    void whenGetBooks_thenBooksShouldBeFound() throws IOException {
        assertTrue(IOUtils.toString(libraryEndpoint.openStream(), defaultCharset()).contains("Asimov"));
    }
}
