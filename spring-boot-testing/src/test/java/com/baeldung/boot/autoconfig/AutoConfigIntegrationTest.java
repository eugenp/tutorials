package com.baeldung.boot.autoconfig;

import static org.junit.Assert.assertEquals;
import io.restassured.RestAssured;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.boot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class AutoConfigIntegrationTest {

    @Test
    public void givenNoAuthentication_whenAccessHome_thenUnauthorized() {
        int statusCode = RestAssured.get("http://localhost:8080/").statusCode();
        assertEquals(HttpStatus.UNAUTHORIZED.value(), statusCode);
    }
    
    @Test
    public void givenAuthentication_whenAccessHome_thenOK() {
        int statusCode = RestAssured.given().auth().basic("john", "123").get("http://localhost:8080/").statusCode();
        assertEquals(HttpStatus.OK.value(), statusCode);
    }
}
