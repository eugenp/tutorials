package com.baeldung.jdbcauthentication.postgre.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * This Live Test requires:
 * *  a PostgreSQL instance running, that allows a 'root' user with password 'pass', and with a database named jdbc_authentication
 * (e.g. with the following command `docker run -p 5432:5432 --name bael-postgre -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=jdbc_authentication postgres:latest`)
 * * the  service up and running
 *
 */
public class UserControllerLiveTest {

    private static final String PRINCIPAL_SVC_URL = "http://localhost:8082/principal";

    @Test
    public void givenExisting_whenRequestPrincipal_thenRetrieveData() throws Exception {
        given().auth()
            .preemptive()
            .basic("user", "pass")
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
