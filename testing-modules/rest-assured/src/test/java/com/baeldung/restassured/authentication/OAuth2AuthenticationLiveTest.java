package com.baeldung.restassured.authentication;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

/**
 * For this Live Test we need:
 * * a running instance of the authorization server located in the spring-security-oauth repo - oauth-authorization-server module.
 * @see <a href="https://github.com/Baeldung/spring-security-oauth/tree/master/oauth-authorization-server">spring-security-oauth/oauth-authorization-server module</a>
 * 
 * * a running instance of the service located in the spring-security-oauth repo - oauth-resource-server-1 module.
 * @see <a href="https://github.com/Baeldung/spring-security-oauth/tree/master/oauth-resource-server-1">spring-security-oauth/oauth-resource-server-1 module</a>
 * 
 */
public class OAuth2AuthenticationLiveTest {

    private static final String USER = "john";
    private static final String PASSWORD = "123";
    private static final String CLIENT_ID = "fooClientIdPassword";
    private static final String SECRET = "secret";
    private static final String AUTH_SVC_TOKEN_URL = "http://localhost:8081/spring-security-oauth-server/oauth/token";
    private static final String RESOURCE_SVC_URL = "http://localhost:8082/spring-security-oauth-resource/foos/1";

    @Test
    public void givenNoAuthentication_whenRequestSecuredResource_thenUnauthorizedResponse() {
        get(RESOURCE_SVC_URL).then()
            .assertThat()
            .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void givenAccessTokenAuthentication_whenRequestSecuredResource_thenResourceRetrieved() {
        String accessToken = given().auth()
            .basic(CLIENT_ID, SECRET)
            .formParam("grant_type", "password")
            .formParam("username", USER)
            .formParam("password", PASSWORD)
            .formParam("scope", "read foo")
            .when()
            .post(AUTH_SVC_TOKEN_URL)
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .path("access_token");

        given().auth()
            .oauth2(accessToken)
            .when()
            .get(RESOURCE_SVC_URL)
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .body("$", hasKey("id"))
            .body("$", hasKey("name"));
    }
}
