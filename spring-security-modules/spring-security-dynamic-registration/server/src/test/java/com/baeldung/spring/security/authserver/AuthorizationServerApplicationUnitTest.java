package com.baeldung.spring.security.authserver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class AuthorizationServerApplicationUnitTest {

    @LocalServerPort
    int serverPort;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void whenGetDiscoveryDoc_thenDynamicRegistrationEndpointEnabled() {

        var discoveryDoc = "http://localhost:" + serverPort + "/.well-known/openid-configuration";
        log.info("Downloading openid-configuration at {}",discoveryDoc);
        var response = restTemplate.getForObject(discoveryDoc, ObjectNode.class);
        assertNotNull(response);
        assertTrue(response.isObject());
        assertTrue(response.has("registration_endpoint"));
        assertTrue(response.get("registration_endpoint").isTextual(), "registration_endpoint should be textual");
        log.info("Registration endpoint: {}",response.get("registration_endpoint"));

    }

    // Dynamic registration "happy-path" test
    @Test
    public void whenDynamicRegistration_thenSuccess() {

        // 1st step: Create registration token
        var discoveryUrl = "http://localhost:" + serverPort + "/.well-known/openid-configuration";
        var discoveryInfo = restTemplate.getForObject(discoveryUrl, ObjectNode.class);

        // resolve endpoints from the returned discovery doc
        var registrationEndpoint = discoveryInfo.get("registration_endpoint").asText();
        var tokenEndpoint = discoveryInfo.get("token_endpoint").asText();
        var authorizationEndpoint = discoveryInfo.get("authorization_endpoint").asText();

        String registrationToken = createRegistrationToken(tokenEndpoint);

        // Create registration request

        var registrationBody = Map.of(
          "client_name", "test-client",
          "grant_types", List.of("authorization_code", "client_credentials"),
          "scope", String.join(" ", "openid email profile"),
          "redirect_uris", List.of("http://localhost:" + serverPort + "/oauth2/callback"));

        var registrationHeaders = new HttpHeaders();
        registrationHeaders.setBearerAuth(registrationToken);
        registrationHeaders.setContentType(MediaType.APPLICATION_JSON);

        var registrationRequest = new RequestEntity<>(
          registrationBody,
          registrationHeaders,
          HttpMethod.POST,
          URI.create(registrationEndpoint));

        var registrationResponse = restTemplate.exchange(registrationRequest, ObjectNode.class);

        assertTrue(registrationResponse.getStatusCode().is2xxSuccessful(), "Registration request should be successful");
        assertNotNull(registrationResponse.getBody());
        var registrationInfo = registrationResponse.getBody();
        assertTrue(registrationInfo.isObject());
        assertTrue(registrationInfo.has("client_id"));
        assertTrue(registrationInfo.has("client_secret"));

        // Issue a test token using client credentials grant

        var tokenRequestBody =new LinkedMultiValueMap<String,String>();
        tokenRequestBody.put("grant_type", List.of("client_credentials"));
        tokenRequestBody.put("scope", List.of("openid email profile"));

        var tokenRequestHeaders = new HttpHeaders();
        tokenRequestHeaders.setBasicAuth(
          registrationInfo.get("client_id").asText(),
          registrationInfo.get("client_secret").asText());
        tokenRequestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var tokenRequest = new RequestEntity<>(
          tokenRequestBody,
          tokenRequestHeaders,
          HttpMethod.POST,
          URI.create(tokenEndpoint));

        var tokenResponse = restTemplate.exchange(tokenRequest, ObjectNode.class);

        assertTrue(tokenResponse.getStatusCode().is2xxSuccessful(), "Token request should be successful");
        assertNotNull(tokenResponse.getBody());
        var tokenInfo = tokenResponse.getBody();
        assertTrue(tokenInfo.isObject());
        assertTrue(tokenInfo.has("access_token"));

    }

    private String createRegistrationToken(String tokenEndpoint) {

        var body = new LinkedMultiValueMap<String,String>();
        body.put("grant_type", List.of("client_credentials"));
        body.put("scope", List.of("client.create"));

        var headers = new HttpHeaders();
        headers.setBasicAuth("registrar-client", "secret");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var request = new RequestEntity<>(
          body,
          headers,
          HttpMethod.POST,
          URI.create(tokenEndpoint));

        var result = restTemplate.exchange(request, ObjectNode.class);
        assertNotNull(result);
        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isObject());
        assertTrue(result.getBody().has("access_token"));

        return result.getBody().get("access_token").asText();
    }
}