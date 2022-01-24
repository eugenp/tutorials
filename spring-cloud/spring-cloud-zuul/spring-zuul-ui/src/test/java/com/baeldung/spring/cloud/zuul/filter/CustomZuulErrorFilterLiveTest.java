package com.baeldung.spring.cloud.zuul.filter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class CustomZuulErrorFilterLiveTest {

    @Test
    public void whenSendRequestWithoutService_thenCustomError() {
        final Response response = RestAssured.get("http://localhost:8080/foos/1");
        assertEquals(503, response.getStatusCode());
    }
    
    @Test
    public void whenSendRequestToWithService_thenOk() {
        final Response response = RestAssured.get("http://localhost:8080/foos/1");
        assertEquals(200, response.getStatusCode());
    }
    

}
