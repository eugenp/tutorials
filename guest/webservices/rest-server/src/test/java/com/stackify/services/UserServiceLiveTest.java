package com.stackify.services;

import org.junit.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

public class UserServiceLiveTest {
    @Test
    public void whenAddUser_thenGetUserOk() {
        RestAssured.baseURI = "http://localhost:8080/rest-server";

        //@formatter:off
        
        String json = "{\"email\":\"john@gmail.com\",\"name\":\"John\"}";
        given()
          .contentType("application/json")
          .body(json)
        .when()
          .post("/users")
        .then()
          .statusCode(200);

        when()
          .get("/users")
        .then()
          .contentType("application/json")
          .body("name", hasItem("John"))
          .body("email", hasItem("john@gmail.com"));
        
        //@formatter:on
    }
}
