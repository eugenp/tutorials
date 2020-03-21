package com.baeldung.jdbcauthentication.h2.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.authentication.FormAuthConfig;
import io.restassured.filter.session.SessionFilter;

/**
 * This Live Test requires the H2JdbcAuthenticationApplication application to be up and running
 */
public class UserControllerLiveTest {

    private static final String PRINCIPAL_SVC_URL = "http://localhost:8082/principal";

    @Test
    public void givenExisting_whenRequestPrincipal_thenRetrieveData() throws Exception {
        SessionFilter filter = new SessionFilter();
        given().auth()
            .form("user", "pass", new FormAuthConfig("/login", "username", "password").withCsrfFieldName("_csrf"))
            .and()
            .filter(filter)
            .when()
            .get(PRINCIPAL_SVC_URL)
            .then()
            .statusCode(HttpStatus.OK.value())
            .and()
            .body("authorities[0].authority", is("ROLE_USER"))
            .body("principal.username", is("user"))
            .body("name", is("user"));
    }
}
