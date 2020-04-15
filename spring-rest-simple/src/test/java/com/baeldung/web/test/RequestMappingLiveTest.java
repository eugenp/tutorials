package com.baeldung.web.test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import com.jayway.restassured.RestAssured;

public class RequestMappingLiveTest {
    private static String BASE_URI = "http://localhost:8082/spring-rest/ex/";

    @Test
    public void givenSimplePath_whenGetFoos_thenOk() {
        RestAssured.given()
            .accept("text/html")
            .get(BASE_URI + "foos")
            .then()
            .assertThat()
            .body(equalTo("Simple Get some Foos"));
    }

    @Test
    public void whenPostFoos_thenOk() {
        RestAssured.given()
            .accept("text/html")
            .post(BASE_URI + "foos")
            .then()
            .assertThat()
            .body(equalTo("Post some Foos"));
    }

    @Test
    public void givenOneHeader_whenGetFoos_thenOk() {
        RestAssured.given()
            .accept("text/html")
            .header("key", "val")
            .get(BASE_URI + "foos")
            .then()
            .assertThat()
            .body(equalTo("Get some Foos with Header"));
    }

    @Test
    public void givenMultipleHeaders_whenGetFoos_thenOk() {
        RestAssured.given()
            .accept("text/html")
            .headers("key1", "val1", "key2", "val2")
            .get(BASE_URI + "foos")
            .then()
            .assertThat()
            .body(equalTo("Get some Foos with Header"));
    }

    @Test
    public void givenAcceptHeader_whenGetFoos_thenOk() {
        RestAssured.given()
            .accept("application/json")
            .get(BASE_URI + "foos")
            .then()
            .assertThat()
            .body(containsString("Get some Foos with Header New"));
    }

    @Test
    public void givenPathVariable_whenGetFoos_thenOk() {
        RestAssured.given()
            .accept("text/html")
            .get(BASE_URI + "foos/1")
            .then()
            .assertThat()
            .body(equalTo("Get a specific Foo with id=1"));
    }

    @Test
    public void givenMultiplePathVariable_whenGetFoos_thenOk() {
        RestAssured.given()
            .accept("text/html")
            .get(BASE_URI + "foos/1/bar/2")
            .then()
            .assertThat()
            .body(equalTo("Get a specific Bar with id=2 from a Foo with id=1"));
    }

    @Test
    public void givenPathVariable_whenGetBars_thenOk() {
        RestAssured.given()
            .accept("text/html")
            .get(BASE_URI + "bars/1")
            .then()
            .assertThat()
            .body(equalTo("Get a specific Bar with id=1"));
    }

    @Test
    public void givenParams_whenGetBars_thenOk() {
        RestAssured.given()
            .accept("text/html")
            .get(BASE_URI + "bars?id=100&second=something")
            .then()
            .assertThat()
            .body(equalTo("Get a specific Bar with id=100"));
    }

    @Test
    public void whenGetFoosOrBars_thenOk() {
        RestAssured.given()
            .accept("text/html")
            .get(BASE_URI + "advanced/foos")
            .then()
            .assertThat()
            .body(equalTo("Advanced - Get some Foos or Bars"));
        RestAssured.given()
            .accept("text/html")
            .get(BASE_URI + "advanced/bars")
            .then()
            .assertThat()
            .body(equalTo("Advanced - Get some Foos or Bars"));
    }
}
