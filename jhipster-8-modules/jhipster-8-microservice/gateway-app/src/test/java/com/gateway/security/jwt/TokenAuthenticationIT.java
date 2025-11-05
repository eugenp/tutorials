package com.gateway.security.jwt;

import static com.gateway.security.jwt.JwtAuthenticationTestUtils.*;

import com.gateway.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_TIMEOUT)
@AuthenticationIntegrationTest
class TokenAuthenticationIT {

    @Autowired
    private WebTestClient webTestClient;

    @Value("${jhipster.security.authentication.jwt.base64-secret}")
    private String jwtKey;

    @Test
    void testLoginWithValidToken() throws Exception {
        expectOk(createValidToken(jwtKey));
    }

    @Test
    void testReturnFalseWhenJWThasInvalidSignature() throws Exception {
        expectUnauthorized(createTokenWithDifferentSignature());
    }

    @Test
    void testReturnFalseWhenJWTisMalformed() throws Exception {
        expectUnauthorized(createSignedInvalidJwt(jwtKey));
    }

    @Test
    void testReturnFalseWhenJWTisExpired() throws Exception {
        expectUnauthorized(createExpiredToken(jwtKey));
    }

    private void expectOk(String token) {
        webTestClient.get().uri("/api/authenticate").headers(headers -> headers.setBearerAuth(token)).exchange().expectStatus().isOk();
    }

    private void expectUnauthorized(String token) {
        webTestClient
            .get()
            .uri("/api/authenticate")
            .headers(headers -> headers.setBearerAuth(token))
            .exchange()
            .expectStatus()
            .isUnauthorized();
    }
}
