package com.baeldung;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserInfoEndpointLiveTest {
    
    @Test
    public void givenAccessToken_whenAccessUserInfoEndpoint_thenSuccess() {
        String accessToken = obtainAccessTokenUsingAuthorizationCodeFlow("john","123");
        Response response = RestAssured.given().header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken).get("http://localhost:8081/auth/user/me");
        
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("john", response.jsonPath().get("name"));
    }
    
    private String obtainAccessTokenUsingAuthorizationCodeFlow(String username, String password) {
        final String authServerUri = "http://localhost:8081/auth";
        final String redirectUrl = "http://www.example.com/";
        final String authorizeUrl = authServerUri + "/oauth/authorize?response_type=code&client_id=SampleClientId&redirect_uri=" + redirectUrl;
        final String tokenUrl = authServerUri + "/oauth/token";

        // user login
        Response response = RestAssured.given().formParams("username", username, "password", password).post(authServerUri + "/login");
        final String cookieValue = response.getCookie("JSESSIONID");
        
        // get authorization code
        RestAssured.given().cookie("JSESSIONID", cookieValue).get(authorizeUrl); 
        response = RestAssured.given().cookie("JSESSIONID", cookieValue).post(authorizeUrl);
        assertEquals(HttpStatus.FOUND.value(), response.getStatusCode());
        final String location = response.getHeader(HttpHeaders.LOCATION);
        final String code = location.substring(location.indexOf("code=") + 5);

        // get access token
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "authorization_code");
        params.put("code", code);
        params.put("client_id", "SampleClientId");
        params.put("redirect_uri", redirectUrl);
        response = RestAssured.given().auth().basic("SampleClientId", "secret").formParams(params).post(tokenUrl);
        return response.jsonPath().getString("access_token");
    }
}
