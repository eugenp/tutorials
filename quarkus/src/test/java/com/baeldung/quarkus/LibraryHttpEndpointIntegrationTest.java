package com.baeldung.quarkus;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(LibraryResource.class)
class LibraryHttpEndpointIntegrationTest {

    @Test
    void testGetBookEndpoint() {
        given().contentType(ContentType.JSON)
               .when()
               .get("book")
               .then()
               .statusCode(200);
    }

}
