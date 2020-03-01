package com.baeldung.web;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.Assert;
import org.junit.Test;


public class IpLiveTest {

    @Test
    public void givenUser_whenGetHomePage_thenOK() {
        final Response response = RestAssured.given().auth().form("john", "123").get("http://localhost:8082/");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue(response.asString().contains("Welcome"));
    }
    
    @Test
    public void givenUserWithWrongIP_whenGetFooById_thenForbidden() {
        final Response response = RestAssured.given().auth().form("john", "123").get("http://localhost:8082/foos/1");
        Assert.assertEquals(403, response.getStatusCode());
        Assert.assertTrue(response.asString().contains("Forbidden"));
    }
   
}