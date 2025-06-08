package com.baeldung.restassured.authentication;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * For this Live Test we need:
 * * a running instance of the service located in the spring-security-rest-basic-auth module.
 * @see <a href="https://github.com/eugenp/tutorials/tree/master/spring-security-rest-basic-auth">spring-security-rest-basic-auth module</a>
 * 
 */
public class BasicAuthenticationLiveTest {

    private static final String USER = "user1";
    private static final String PASSWORD = "user1Pass";
    private static final String SVC_URL = "http://localhost:8080/spring-security-rest-basic-auth/api/foos/1";

    @Test
    public void givenNoAuthentication_whenRequestSecuredResource_thenUnauthorizedResponse() {
        get(SVC_URL).then()
            .assertThat()
            .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void givenBasicAuthentication_whenRequestSecuredResource_thenResourceRetrieved() {
        given().auth()
            .basic(USER, PASSWORD)
            .when()
            .get(SVC_URL)
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value());
    }
}
