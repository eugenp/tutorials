package com.baeldung.keycloaktestcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

class UserControllerIntegrationTest extends IntegrationTest {

    @Test
    void givenAuthenticatedUser_whenGetMe_shouldReturnMyInfo() {

        given().header("Authorization", getJaneDoeBearer())
            .when()
            .get("/users/me")
            .then()
            .body("username", equalTo("janedoe"))
            .body("lastname", equalTo("Doe"))
            .body("firstname", equalTo("Jane"))
            .body("email", equalTo("jane.doe@baeldung.com"));

    }
}
