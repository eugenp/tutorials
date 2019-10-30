package com.baeldung.restassured.authentication;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.http.ContentType;

/**
 * For this Live Test we need to obtain a valid Access Token and Token Secret:
 * * start spring-mvc-simple application in debug mode
 * @see <a href="https://github.com/eugenp/tutorials/tree/master/spring-mvc-simple">spring-mvc-simple module</a>
 * * calling localhost:8080/spring-mvc-simple/twitter/authorization/ using the browser
 * * debug the callback function where we can obtain the fields
 */
public class OAuthAuthenticationLiveTest {

    // We can obtain these two from the spring-mvc-simple / TwitterController class
    private static final String OAUTH_API_KEY = "PSRszoHhRDVhyo2RIkThEbWko";
    private static final String OAUTH_API_SECRET = "prpJbz03DcGRN46sb4ucdSYtVxG8unUKhcnu3an5ItXbEOuenL";
    private static final String TWITTER_ENDPOINT = "https://api.twitter.com/1.1/account/settings.json";
    /* We can obtain the following by:
     * - starting the spring-mvc-simple application
     * - calling localhost:8080/spring-mvc-simple/twitter/authorization/
     * - debugging the callback function */
    private static final String ACCESS_TOKEN = "...";
    private static final String TOKEN_SECRET = "...";

    @Test
    public void givenNoAuthentication_whenRequestSecuredResource_thenUnauthorizedResponse() {
        get(TWITTER_ENDPOINT).then()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void givenAccessTokenAuthentication_whenRequestSecuredResource_thenResourceIsRequested() {
        given().accept(ContentType.JSON)
            .auth()
            .oauth(OAUTH_API_KEY, OAUTH_API_SECRET, ACCESS_TOKEN, TOKEN_SECRET)
            .when()
            .get(TWITTER_ENDPOINT)
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .body("$", hasKey("geo_enabled"))
            .body("$", hasKey("language"));
    }

}
