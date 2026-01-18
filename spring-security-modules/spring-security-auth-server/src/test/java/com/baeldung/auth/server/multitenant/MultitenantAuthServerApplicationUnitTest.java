package com.baeldung.auth.server.multitenant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MultitenantAuthServerApplicationUnitTest {

    @LocalServerPort
    private int port;

    private RestTestClient restTestClient;

    @Autowired
    ApplicationContext ctx;

    @Test
    void whenSpringContextIsBootstrapped_thenNoExceptions() {
        assertNotNull(ctx);
    }

    @Test
    void whenRequestDiscoveryDocumentForIssuer1_thenSuccess() {
        restTestClient.get()
          .uri("/issuer1/.well-known/openid-configuration")
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody()
          .jsonPath("$.issuer")
          .isEqualTo("http://localhost:" + port + "/issuer1");
    }

    @Test
    void whenRequestDiscoveryDocumentForIssuer2_thenSuccess() {
        restTestClient.get()
          .uri("/issuer2/.well-known/openid-configuration")
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody()
          .jsonPath("$.issuer")
          .isEqualTo("http://localhost:" + port + "/issuer2");
    }

    @Test
    void givenClientCredentialsAndValidScope_whenRequestTokenForIssuer1_thenSuccess() {

        var response = restTestClient.post()
          .uri("/issuer1/oauth2/token")
          .header("Authorization", "Basic " + base64Encode("client1:secret1"))
          .header("Content-Type", "application/x-www-form-urlencoded")
          .body("grant_type=client_credentials&scope=account:read")
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody()
          .jsonPath("$.access_token")
          .exists()
          .returnResult()
          .getResponseBodyContent();

        assertNotNull(response);
    }

    @Test
    void givenClientCredentialsAndValidScope_whenRequestTokenForIssuer2_thenSuccess() {

        var response = restTestClient.post()
          .uri("/issuer2/oauth2/token")
          .header("Authorization", "Basic " + base64Encode("client1:secret1"))
          .header("Content-Type", "application/x-www-form-urlencoded")
          .body("grant_type=client_credentials&scope=account:write")
          .exchange()
          .expectStatus()
          .isOk()
          .expectBody()
          .jsonPath("$.access_token")
          .exists()
          .returnResult()
          .getResponseBodyContent();

        assertNotNull(response);
    }

    @Test
    void givenClientCredentialsAndinalidScope_whenRequestTokenForIssuer1_thenError() {

        restTestClient.post()
          .uri("/issuer1/oauth2/token")
          .header("Authorization", "Basic " + base64Encode("client1:secret1"))
          .header("Content-Type", "application/x-www-form-urlencoded")
          .body("grant_type=client_credentials&scope=account:write") // Invalid scope for Tenant1
          .exchange()
          .expectStatus()
          .is4xxClientError();
    }

    @BeforeEach
    void setupRestClient() {
        restTestClient = RestTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    private static String base64Encode(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes());
    }
}