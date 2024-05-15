package com.baeldung.restassured.authentication;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isEmptyString;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.authentication.FormAuthConfig;

/**
 * For this Live Test we need:
 * * a running instance of the service located in the spring-security-mvc-login module.
 * @see <a href="https://github.com/eugenp/tutorials/tree/master/spring-security-mvc-login">spring-security-mvc-login module</a>
 * 
 */
public class FormAuthenticationLiveTest {

    private static final String USER = "user1";
    private static final String PASSWORD = "user1Pass";
    private static final String SVC_URL = "http://localhost:8080/spring-security-mvc-login/secured";

    @Test
    public void givenNoAuthentication_whenRequestSecuredResource_thenLoginFormResponse() {
        get(SVC_URL).then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .body(containsString("<form"), containsString("action=\"perform_login\""));
    }

    @Test
    public void givenParsingFormAuthentication_whenRequestSecuredResource_thenLoginFormResponse() {
        // Form can't be parsed correctly because the app is in servlet container, thus the form's 'action' attribute doesn't include the correct URI
        given().auth()
            .form(USER, PASSWORD)
            .when()
            .get(SVC_URL)
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .body(containsString("<form"), containsString("action=\"perform_login\""));
    }

    @Test
    public void givenFormAuthentication_whenRequestSecuredResource_thenResourceRetrieved() {
        given().auth()
            .form(USER, PASSWORD, new FormAuthConfig("/spring-security-mvc-login/perform_login", "username", "password"))
            .when()
            .get(SVC_URL)
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .body(isEmptyString());
    }
}
