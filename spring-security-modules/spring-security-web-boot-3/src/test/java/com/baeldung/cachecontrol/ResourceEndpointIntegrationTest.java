package com.baeldung.cachecontrol;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AppRunner.class)
class ResourceEndpointIntegrationTest {

    @LocalServerPort
    private int serverPort;

    @Test
    void whenGetRequestForUser_shouldRespondWithDefaultCacheHeaders() {
        given().when().get(getBaseUrl() + "/default/users/Michael").then().headers("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate").header("Pragma", "no-cache");
    }

    @Test
    void whenGetRequestForUser_shouldRespondMaxAgeCacheControl() {
        given().when().get(getBaseUrl() + "/users/Michael").then().header("Cache-Control", "max-age=60");
    }

    @Test
    void givenServiceEndpoint_whenGetRequestForUser_shouldResponseWithCacheControlMaxAge() {
        given().when().get(getBaseUrl() + "/users/Michael").then().contentType(ContentType.JSON).and().statusCode(200).and().header("Cache-Control", "max-age=60");
    }

    @Test
    void givenServiceEndpoint_whenGetRequestForNotCacheableContent_shouldResponseWithCacheControlNoCache() {
        given().when().get(getBaseUrl() + "/timestamp").then().contentType(ContentType.JSON).and().statusCode(200).and().header("Cache-Control", "no-store");
    }

    @Test
    void givenServiceEndpoint_whenGetRequestForPrivateUser_shouldResponseWithSecurityDefaultCacheControl() {
        given().when().get(getBaseUrl() + "/private/users/Michael").then().contentType(ContentType.JSON).and().statusCode(200).and().header("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
    }

    private String getBaseUrl() {
        return String.format("http://localhost:%d", serverPort);
    }

}