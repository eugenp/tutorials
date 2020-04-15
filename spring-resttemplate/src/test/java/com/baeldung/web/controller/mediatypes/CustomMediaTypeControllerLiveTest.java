package com.baeldung.web.controller.mediatypes;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static io.restassured.RestAssured.given;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
public class CustomMediaTypeControllerLiveTest {
    private static final String URL_PREFIX = "http://localhost:8082/spring-rest";

    @Test
    public void givenServiceEndpoint_whenGetRequestFirstAPIVersion_thenShouldReturn200() {
        given()
                .accept("application/vnd.baeldung.api.v1+json")
        .when()
                .get(URL_PREFIX + "/public/api/items/1")
        .then()
                .contentType(ContentType.JSON).and().statusCode(200);
    }


    @Test
    public void givenServiceEndpoint_whenGetRequestSecondAPIVersion_thenShouldReturn200() {
        given()
                .accept("application/vnd.baeldung.api.v2+json")
        .when()
                .get(URL_PREFIX + "/public/api/items/2")
        .then()
                .contentType(ContentType.JSON).and().statusCode(200);
    }
}
