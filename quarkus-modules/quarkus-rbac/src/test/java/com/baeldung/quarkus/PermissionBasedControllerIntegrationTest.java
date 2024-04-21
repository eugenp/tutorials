package com.baeldung.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class PermissionBasedControllerIntegrationTest {

    @Test
    @TestSecurity(user = "admin", roles = "VIEW_ADMIN_DETAILS")
    @JwtSecurity(claims = {
        @Claim(key = "email", value = "admin@test.io")
    })
    void givenSecureVersionApi_whenUserIsAuthenticated_thenShouldReturnVersion() {
        given()
                .contentType(ContentType.JSON)
                .get("/permission-based/resource/version")
                .then()
                .statusCode(200)
                .body(equalTo("2.0.0"));
    }

    @Test
    @TestSecurity(user = "user", roles = "SEND_MESSAGE")
    @JwtSecurity(claims = {
        @Claim(key = "email", value = "user@test.io")
    })
    void givenSecureMessageApi_whenUserOnlyHasOnePermission_thenShouldNotAllowRequest() {
        given()
                .contentType(ContentType.JSON)
                .get("/permission-based/resource/message")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "new-operator", roles = {"SEND_MESSAGE", "OPERATOR"})
    @JwtSecurity(claims = {
        @Claim(key = "email", value = "operator@test.io")
    })
    void givenSecureMessageApi_whenUserOnlyHasBothPermissions_thenShouldAllowRequest() {
        given()
                .contentType(ContentType.JSON)
                .get("/permission-based/resource/message")
                .then()
                .statusCode(200)
                .body("message", equalTo("Hello new-operator!"));
    }

}