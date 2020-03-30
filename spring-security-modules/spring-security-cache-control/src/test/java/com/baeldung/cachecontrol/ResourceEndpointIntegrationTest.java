package com.baeldung.cachecontrol;

import static io.restassured.RestAssured.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AppRunner.class)
public class ResourceEndpointIntegrationTest {

    @LocalServerPort
    private int serverPort;

    @Test
    public void whenGetRequestForUser_shouldRespondWithDefaultCacheHeaders() {
        given().when().get(getBaseUrl() + "/default/users/Michael").then().headers("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate").header("Pragma", "no-cache");
    }

    @Test
    public void whenGetRequestForUser_shouldRespondMaxAgeCacheControl() {
        given().when().get(getBaseUrl() + "/users/Michael").then().header("Cache-Control", "max-age=60");
    }

    @Test
    public void givenServiceEndpoint_whenGetRequestForUser_shouldResponseWithCacheControlMaxAge() {
        given().when().get(getBaseUrl() + "/users/Michael").then().contentType(ContentType.JSON).and().statusCode(200).and().header("Cache-Control", "max-age=60");
    }

    @Test
    public void givenServiceEndpoint_whenGetRequestForNotCacheableContent_shouldResponseWithCacheControlNoCache() {
        given().when().get(getBaseUrl() + "/timestamp").then().contentType(ContentType.JSON).and().statusCode(200).and().header("Cache-Control", "no-store");
    }

    @Test
    public void givenServiceEndpoint_whenGetRequestForPrivateUser_shouldResponseWithSecurityDefaultCacheControl() {
        given().when().get(getBaseUrl() + "/private/users/Michael").then().contentType(ContentType.JSON).and().statusCode(200).and().header("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
    }

    private String getBaseUrl() {
        return String.format("http://localhost:%d", serverPort);
    }

}