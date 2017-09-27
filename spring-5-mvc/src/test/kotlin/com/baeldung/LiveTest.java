package com.baeldung;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.http.MediaType;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class LiveTest {

    private static String APP_ROOT = "http://localhost:8081";

    @Test
    public void givenUser_whenResourceCreatedWithNullName_then400BadRequest() {
        final Response response = givenAuth("user", "pass").contentType(MediaType.APPLICATION_JSON.toString())
            .body(resourceWithNullName())
            .post(APP_ROOT + "/foos");
        assertEquals(400, response.getStatusCode());
    }

    @Test
    public void givenUser_whenResourceCreated_then201Created() {
        final Response response = givenAuth("user", "pass").contentType(MediaType.APPLICATION_JSON.toString())
            .body(resourceString())
            .post(APP_ROOT + "/foos");
        assertEquals(201, response.getStatusCode());
    }

    /*@Test
    public void givenUser_whenGetAllFoos_thenOK() {
        final Response response = givenAuth("user", "pass").get(APP_ROOT + "/foos");
        assertEquals(200, response.getStatusCode());
    }*/

    //

    private final String resourceWithNullName() {
        return "{\"name\":null}";
    }

    private final String resourceString() {
        return "{\"name\":\"" + randomAlphabetic(8) + "\"}";
    }

    private final RequestSpecification givenAuth(String username, String password) {
        return RestAssured.given()
            .auth()
            .preemptive()
            .basic(username, password);
    }

}