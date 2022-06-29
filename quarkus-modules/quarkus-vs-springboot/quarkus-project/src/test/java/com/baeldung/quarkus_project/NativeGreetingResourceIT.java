package com.baeldung.quarkus_project;

import io.quarkus.test.junit.NativeImageTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@NativeImageTest
@QuarkusTest
public class NativeGreetingResourceIT {

    @Test
    void testEndpoint() {
        given()
            .when().get("/hello")
            .then()
            .statusCode(200);
    }

}