package com.baeldung.restassured;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredAdvancedLiveTest {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://api.github.com";
        RestAssured.port = 443;
    }

    // ====== request
    @Test
    public void whenRequestGet_thenOK() {
        when().request("GET", "/users/eugenp")
            .then()
            .statusCode(200);
    }

    @Test
    public void whenRequestHead_thenOK() {
        when().request("HEAD", "/users/eugenp")
            .then()
            .statusCode(200);
    }

    @Test
    public void whenMeasureResponseTime_thenOK() {
        Response response = RestAssured.get("/users/eugenp");
        long timeInMS = response.time();
        long timeInS = response.timeIn(TimeUnit.SECONDS);

        assertEquals(timeInS, timeInMS / 1000);
    }

    @Test
    public void whenValidateResponseTime_thenSuccess() {
        when().get("/users/eugenp")
            .then()
            .time(lessThan(5000L));
    }

    @Test
    public void whenValidateResponseTimeInSeconds_thenSuccess() {
        when().get("/users/eugenp")
            .then()
            .time(lessThan(5L), TimeUnit.SECONDS);
    }

    //======= log

    @Test
    public void whenLogRequest_thenOK(){
        given().log().all().when().get("/users/eugenp").then().statusCode(200);
    }

    @Test
    public void whenLogResponse_thenOK(){
        when().get("/repos/eugenp/tutorials").then().log().body().statusCode(200);
    }

    @Test
    public void whenLogResponseIfErrorOccurred_thenSuccess(){
        when().get("/users/eugenp").then().log().ifError();
        when().get("/users/eugenp").then().log().ifStatusCodeIsEqualTo(500);
        when().get("/users/eugenp").then().log().ifStatusCodeMatches(greaterThan(200));
    }

    @Test
    public void whenLogOnlyIfValidationFailed_thenSuccess(){
        when().get("/users/eugenp").then().log().ifValidationFails().statusCode(200);
        given().log().ifValidationFails().when().get("/users/eugenp").then().statusCode(200);
    }
}
