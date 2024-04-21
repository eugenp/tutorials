package com.baeldung.quarkus;

import com.baeldung.quarkus.rbac.api.LoginDto;
import com.baeldung.quarkus.rbac.api.Message;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@QuarkusTest
class SecureResourceControllerIntegrationTest {

    @Test
    void givenSecureLoginApi_whenAdminLogsIn_thenShouldReturnOK() {
        given()
            .contentType(ContentType.JSON)
            .body(new LoginDto("admin", "admin"), ObjectMapperType.JACKSON_2)
            .post("/secured/login")
            .then()
            .statusCode(200)
            .body("token", notNullValue())
            .body("expiresIn", equalTo("3600"));
    }

    @Test
    @TestSecurity(user = "user", roles = "VIEW_USER_DETAILS")
    @JwtSecurity(claims = {
        @Claim(key = "email", value = "user@test.io")
    })
    void givenSecureUserApi_whenUserIsAuthenticated_thenShouldReturnCustomMessage() {
        given()
                .contentType(ContentType.JSON)
                .get("/secured/resource/user")
                .then()
                .statusCode(200)
                .body("message", equalTo("Hello user!"));;
    }

    @Test
    @TestSecurity(user = "user", roles = "VIEW_USER_DETAILS")
    @JwtSecurity(claims = {
        @Claim(key = "email", value = "user@test.io")
    })
    void givenSecureAdminApi_whenUserTriesToAccessAdminApi_thenShouldNotAllowRequest() {
        given()
                .contentType(ContentType.JSON)
                .get("/secured/resource")
                .then()
                .statusCode(403);
    }

    @Test
    @TestSecurity(user = "admin", roles = "VIEW_ADMIN_DETAILS")
    @JwtSecurity(claims = {
        @Claim(key = "email", value = "admin@test.io")
    })
    void givenSecureAdminApi_whenAdminTriesAccessAdminApi_thenShouldAllowRequest() {
        given()
                .contentType(ContentType.JSON)
                .get("/secured/resource")
                .then()
                .statusCode(200)
                .body(equalTo("Hello world, here are some details about the admin!"));
    }

    @Test
    @TestSecurity(user = "guest", roles = "SEND_MESSAGE")
    @JwtSecurity(claims = {
        @Claim(key = "email", value = "guest@test.io")
    })
    void givenSecureGuestApi_whenCallAsGuess_thenShouldReturnOk() {
        given()
            .contentType(ContentType.JSON)
            .body(new Message("Hello Friend!"), ObjectMapperType.JACKSON_2)
            .post("/secured/resource")
            .then()
            .statusCode(200)
            .body("message", equalTo("Hello Friend!"));
    }
}