package com.baeldung.quarkus.resources;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
class ResourceAccessAPIUnitTest {
    @Test
    @DisplayName("should return content from default resource")
    void givenAPI_whenGetDefaultResource_thenReturnsContent() {
        given()
            .when().get("/resources/default")
            .then()
            .statusCode(200)
            .body(is("This is the default resource."));
    }

    @Test
    @DisplayName("should return content from nested default resource")
    void givenAPI_whenGetDefaultNestedResource_thenReturnsContent() {
        given()
            .when().get("/resources/default-nested")
            .then()
            .statusCode(200)
            .body(is("This is another resource from a sub-directory."));
    }

    @Test
    @DisplayName("should return content from included json resource")
    void givenAPI_whenGetJsonResource_thenReturnsContent() {
        given()
            .when().get("/resources/json")
            .then()
            .statusCode(200)
            .body("version", is("1.0.0"));
    }


    @Test
    @DisplayName("should return content from index.html")
    void givenIndexPage_whenGetRootUrl_thenReturnsContent() {
        given()
            .when().get("/")
            .then()
            .statusCode(200)
            .body(containsString("Hello"));
    }
}