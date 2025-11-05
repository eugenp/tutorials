package com.gateway.security.jwt;

import static com.gateway.security.jwt.JwtAuthenticationTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.gateway.IntegrationTest;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient(timeout = IntegrationTest.DEFAULT_TIMEOUT)
@AuthenticationIntegrationTest
class TokenAuthenticationSecurityMetersIT {

    private static final String INVALID_TOKENS_METER_EXPECTED_NAME = "security.authentication.invalid-tokens";

    @Autowired
    private WebTestClient webTestClient;

    @Value("${jhipster.security.authentication.jwt.base64-secret}")
    private String jwtKey;

    @Autowired
    private MeterRegistry meterRegistry;

    @Test
    void testValidTokenShouldNotCountAnything() throws Exception {
        Collection<Counter> counters = meterRegistry.find(INVALID_TOKENS_METER_EXPECTED_NAME).counters();

        var count = aggregate(counters);

        tryToAuthenticate(createValidToken(jwtKey));

        assertThat(aggregate(counters)).isEqualTo(count);
    }

    @Test
    void testTokenExpiredCount() throws Exception {
        var count = meterRegistry.get(INVALID_TOKENS_METER_EXPECTED_NAME).tag("cause", "expired").counter().count();

        tryToAuthenticate(createExpiredToken(jwtKey));

        assertThat(meterRegistry.get(INVALID_TOKENS_METER_EXPECTED_NAME).tag("cause", "expired").counter().count()).isEqualTo(count + 1);
    }

    @Test
    void testTokenSignatureInvalidCount() throws Exception {
        var count = meterRegistry.get(INVALID_TOKENS_METER_EXPECTED_NAME).tag("cause", "invalid-signature").counter().count();

        tryToAuthenticate(createTokenWithDifferentSignature());

        assertThat(meterRegistry.get(INVALID_TOKENS_METER_EXPECTED_NAME).tag("cause", "invalid-signature").counter().count()).isEqualTo(
            count + 1
        );
    }

    @Test
    void testTokenMalformedCount() throws Exception {
        var count = meterRegistry.get(INVALID_TOKENS_METER_EXPECTED_NAME).tag("cause", "malformed").counter().count();

        tryToAuthenticate(createSignedInvalidJwt(jwtKey));

        assertThat(meterRegistry.get(INVALID_TOKENS_METER_EXPECTED_NAME).tag("cause", "malformed").counter().count()).isEqualTo(count + 1);
    }

    @Test
    void testTokenInvalidCount() throws Exception {
        var count = meterRegistry.get(INVALID_TOKENS_METER_EXPECTED_NAME).tag("cause", "malformed").counter().count();

        tryToAuthenticate(createInvalidToken(jwtKey));

        assertThat(meterRegistry.get(INVALID_TOKENS_METER_EXPECTED_NAME).tag("cause", "malformed").counter().count()).isEqualTo(count + 1);
    }

    private void tryToAuthenticate(String token) {
        webTestClient
            .get()
            .uri("/api/authenticate")
            .headers(headers -> headers.setBearerAuth(token))
            .exchange()
            .returnResult(String.class)
            .getResponseBody()
            .blockLast();
    }

    private double aggregate(Collection<Counter> counters) {
        return counters.stream().mapToDouble(Counter::count).sum();
    }
}
