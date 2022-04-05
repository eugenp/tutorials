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

import org.junit.Before;
import org.junit.Test;

public class RestAssuredAdvancedLiveTest {
    
    @Before
    public void setup(){
        RestAssured.baseURI = "https://api.github.com";
        RestAssured.port = 443;
    }
    
    @Test
    public void whenMeasureResponseTime_thenOK(){
        Response response = RestAssured.get("/users/eugenp");
        long timeInMS = response.time();
        long timeInS = response.timeIn(TimeUnit.SECONDS);
        
        assertEquals(timeInS, timeInMS/1000);
    }
    
    @Test
    public void whenValidateResponseTime_thenSuccess(){
        when().get("/users/eugenp").then().time(lessThan(5000L));
    }

    @Test
    public void whenValidateResponseTimeInSeconds_thenSuccess(){
        when().get("/users/eugenp").then().time(lessThan(5L),TimeUnit.SECONDS);
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
    
    // ====== request
    
    @Test
    public void whenRequestGet_thenOK(){
        when().request("GET", "/users/eugenp").then().statusCode(200);
    }
    
    @Test
    public void whenRequestHead_thenOK(){
        when().request("HEAD", "/users/eugenp").then().statusCode(200);
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
