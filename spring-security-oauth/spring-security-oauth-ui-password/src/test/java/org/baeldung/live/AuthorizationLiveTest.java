package org.baeldung.live;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class AuthorizationLiveTest {

    private String obtainAccessToken(String clientId, String username, String password) {
        final Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", "password");
        params.put("client_id", clientId);
        params.put("username", username);
        params.put("password", password);
        final Response response = RestAssured.given().auth().preemptive().basic(clientId, "secret").and().with().params(params).when().post("http://localhost:8081/spring-security-oauth-server/oauth/token");
        return response.jsonPath().getString("access_token");
    }

    @Test
    public void givenUser_whenUseFooClient_thenOkForFooResourceOnly() {
        final String accessToken = obtainAccessToken("fooClientIdPassword", "john", "123");

        final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/foos/1");
        assertEquals(200, fooResponse.getStatusCode());
        assertNotNull(fooResponse.jsonPath().get("name"));

        final Response barResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/bars/1");
        assertEquals(403, barResponse.getStatusCode());

        final Response bazResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/bazes/1");
        assertEquals(403, bazResponse.getStatusCode());
    }

    @Test
    public void givenUser_whenUseBarClient_thenOkForBarResourceOnly() {
        final String accessToken = obtainAccessToken("barClientIdPassword", "john", "123");

        final Response barResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/bars/1");
        assertEquals(200, barResponse.getStatusCode());
        assertNotNull(barResponse.jsonPath().get("name"));

        final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/foos/1");
        assertEquals(403, fooResponse.getStatusCode());

        final Response bazResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/bazes/1");
        assertEquals(403, bazResponse.getStatusCode());
    }

    @Test
    public void givenAdmin_whenUseFooClient_thenOkForFooAndBazResourceOnly() {
        final String accessToken = obtainAccessToken("fooClientIdPassword", "tom", "111");

        final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/foos/1");
        assertEquals(200, fooResponse.getStatusCode());
        assertNotNull(fooResponse.jsonPath().get("name"));

        final Response bazResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/bazes/1");
        assertEquals(200, bazResponse.getStatusCode());
        assertNotNull(bazResponse.jsonPath().get("name"));

        final Response barResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/bars/1");
        assertEquals(403, barResponse.getStatusCode());
    }

    @Test
    public void givenAdmin_whenUseBarClient_thenOkForBarAndBazResourceOnly() {
        final String accessToken = obtainAccessToken("barClientIdPassword", "tom", "111");

        final Response barResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/bars/1");
        assertEquals(200, barResponse.getStatusCode());
        assertNotNull(barResponse.jsonPath().get("name"));

        final Response bazResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/bazes/1");
        assertEquals(200, bazResponse.getStatusCode());
        assertNotNull(bazResponse.jsonPath().get("name"));

        final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get("http://localhost:8081/spring-security-oauth-resource/foos/1");
        assertEquals(403, fooResponse.getStatusCode());
    }

}
