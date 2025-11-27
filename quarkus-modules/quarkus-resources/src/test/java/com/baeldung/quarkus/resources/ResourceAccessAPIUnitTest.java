package com.baeldung.quarkus.resources;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ResourceAccessAPIUnitTest {
    @Test
    @DisplayName("should return content from default resource")
    void whenGetDefaultResource_thenReturnsContent() {
        given()
            .when().get("/resources/default")
            .then()
                .statusCode(200)
                .body(is("This is the default resource."));
    }

    @Test
    @DisplayName("should return content from nested default resource")
    void whenGetDefaultNestedResource_thenReturnsContent() {
        given()
            .when().get("/resources/default-nested")
            .then()
                .statusCode(200)
                .body(is("This is another resource from a sub-directory."));
    }

    @Test
    @DisplayName("should return content from included json resource")
    void whenGetJsonResource_thenReturnsContent() {
        given()
            .when().get("/resources/json")
            .then()
            .statusCode(200)
            .body("version", is("1.0.0"));
    }

}