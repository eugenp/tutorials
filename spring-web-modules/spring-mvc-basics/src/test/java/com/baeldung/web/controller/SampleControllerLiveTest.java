package com.baeldung.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;

public class SampleControllerLiveTest {

    private static final String SERVICE_BASE_URL = "/spring-mvc-basics";

    @Test
    public void whenSampleEndpointCalled_thenOkResponseObtained() throws Exception {
        RestAssured.get(SERVICE_BASE_URL + "/sample")
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void whenSample2EndpointCalled_thenOkResponseObtained() throws Exception {
        RestAssured.get(SERVICE_BASE_URL + "/sample2")
            .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void whenSample3EndpointCalled_thenOkResponseObtained() throws Exception {
        RestAssured.get(SERVICE_BASE_URL + "/sample3")
            .then()
            .statusCode(HttpStatus.OK.value());
    }

}
