package com.baeldung.jdbcauthentication.mysql.web;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * This Live Test requires:
 * *  a MySql instance running, that allows a 'root' user with password 'pass', and with a database named jdbc_authentication
 * (e.g. with the following command `docker run -p 3306:3306 --name bael-mysql -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=jdbc_authentication mysql:latest`)
 * * the service up and running
 *
 */
public class UserControllerLiveTest {

    private static final String PRINCIPAL_SVC_URL = "http://localhost:8082/principal";

    @Test
    public void givenExisting_whenRequestPrincipal_thenRetrieveData() throws Exception {
        given().auth()
            .preemptive()
            .basic("user@email.com", "pass")
            .when()
            .get(PRINCIPAL_SVC_URL)
            .then()
            .statusCode(HttpStatus.OK.value())
            .and()
            .body("authorities[0].authority", is("ROLE_USER"))
            .body("principal.username", is("user@email.com"))
            .body("name", is("user@email.com"));
    }

}
