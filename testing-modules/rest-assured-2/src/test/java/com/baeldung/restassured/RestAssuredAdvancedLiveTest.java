package com.baeldung.restassured;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertEquals;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.response.Response;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RestAssuredAdvancedLiveTest {
    
    @BeforeEach
    public void setup(){
        RestAssured.baseURI = "https://api.github.com";
        RestAssured.port = 443;
    }

    //===== parameter
    
    @Test
    public void whenUseQueryParam_thenOK(){
        given().queryParam("q", "john").when().get("/search/users").then().statusCode(200);
        given().param("q", "john").when().get("/search/users").then().statusCode(200);
    }
    
    @Test
    public void whenUseMultipleQueryParam_thenOK(){
        int perPage = 20;
        given().queryParam("q", "john").queryParam("per_page",perPage).when().get("/search/users").then().body("items.size()", is(perPage));        
        given().queryParams("q", "john","per_page",perPage).when().get("/search/users").then().body("items.size()", is(perPage));
    }
    
    @Test
    public void whenUseFormParam_thenSuccess(){
        given().log().all().formParams("username", "john","password","1234").post("/");
        given().log().all().params("username", "john","password","1234").post("/");
    }
    
    @Test
    public void whenUsePathParam_thenOK(){
        given().pathParam("user", "eugenp").when().get("/users/{user}/repos").then().log().all().statusCode(200);
    }
    
    @Test
    public void whenUseMultiplePathParam_thenOK(){
        given().log().all().pathParams("owner", "eugenp","repo","tutorials").when().get("/repos/{owner}/{repo}").then().statusCode(200);
        given().log().all().pathParams("owner", "eugenp").when().get("/repos/{owner}/{repo}","tutorials").then().statusCode(200);
    }
    
    //===== header
    
    @Test
    public void whenUseCustomHeader_thenOK(){
        given().header("User-Agent", "MyAppName").when().get("/users/eugenp").then().statusCode(200);
    }
    
    @Test
    public void whenUseMultipleHeaders_thenOK(){
        given().headers("User-Agent", "MyAppName","Accept-Charset","utf-8").when().get("/users/eugenp").then().statusCode(200);
    }    
    
    //======= cookie
    
    @Test
    public void whenUseCookie_thenOK(){
        given().cookie("session_id", "1234").when().get("/users/eugenp").then().statusCode(200);
    }
    
    @Test
    public void whenUseCookieBuilder_thenOK(){
        Cookie myCookie = new Cookie.Builder("session_id", "1234").setSecured(true).setComment("session id cookie").build();
        given().cookie(myCookie).when().get("/users/eugenp").then().statusCode(200);
    }

}
