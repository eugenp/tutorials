package org.baeldung.web;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class LiveTest {

    @Test
    public void whenSendRequestToFooResource_thenOK() {
        final Response response = RestAssured.get("http://localhost:8080/foos/1");
        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void whenSendRequest_thenHeaderAdded() {
        final Response response = RestAssured.get("http://localhost:8080/foos/1");
        assertEquals(200, response.getStatusCode());
        assertEquals("TestSample", response.getHeader("Test"));
    }
}
