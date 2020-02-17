package org.baeldung.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.Test;


public class IpLiveTest {

    @Test
    public void givenUser_whenGetHomePage_thenOK() {
        final Response response = RestAssured.given().auth().form("john", "123").get("http://localhost:8082/");
        assertEquals(200, response.getStatusCode());
        assertTrue(response.asString().contains("Welcome"));
    }
    
    @Test
    public void givenUserWithWrongIP_whenGetFooById_thenForbidden() {
        final Response response = RestAssured.given().auth().form("john", "123").get("http://localhost:8082/foos/1");
        assertEquals(403, response.getStatusCode());
        assertTrue(response.asString().contains("Forbidden"));
    }
   
}