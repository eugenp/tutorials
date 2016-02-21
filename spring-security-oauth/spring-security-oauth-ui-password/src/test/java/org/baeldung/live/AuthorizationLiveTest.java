package org.baeldung.live;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class AuthorizationLiveTest {

    private String obtainAccessToken(String username, String password) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "password");
        params.put("client_id", "clientIdPassword");
        params.put("username", username);
        params.put("password", password);
        final Response response = RestAssured.given().auth().preemptive().basic("clientIdPassword", "secret").and().with().params(params).when().post("http://localhost:8081/spring-security-oauth-server/oauth/token");
        return response.jsonPath().getString("access_token");
    }

    @Test
    public void givenUser_whenAccessFoosResource_thenOk() {
        final String accessToken = obtainAccessToken("john", "123");
        final Response response = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/foos/1");
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.jsonPath().get("name"));
    }

    @Test
    public void givenUser_whenAccessBarssResource_thenUnauthorized() {
        final String accessToken = obtainAccessToken("john", "123");
        final Response response = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/bars/1");
        assertEquals(403, response.getStatusCode());
    }

    @Test
    public void givenAdmin_whenAccessFoosResource_thenOk() {
        final String accessToken = obtainAccessToken("tom", "111");
        final Response response = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/foos/1");
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.jsonPath().get("name"));
    }

    @Test
    public void givenAdmin_whenAccessBarssResource_thenOk() {
        final String accessToken = obtainAccessToken("tom", "111");
        final Response response = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/bars/1");
        assertEquals(200, response.getStatusCode());
        assertNotNull(response.jsonPath().get("name"));
    }

}
