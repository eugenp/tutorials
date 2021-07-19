package com.baeldung.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import com.baeldung.multipleauthproviders.MultipleAuthProvidersApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = MultipleAuthProvidersApplication.class)
public class MultipleAuthProvidersApplicationIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenMemUsers_whenGetPingWithValidUser_thenOk() {
        ResponseEntity<String> result = makeRestCallToGetPing("memuser", "pass");

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("OK");
    }

    @Test
    public void givenExternalUsers_whenGetPingWithValidUser_thenOK() {
        ResponseEntity<String> result = makeRestCallToGetPing("externaluser", "pass");

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("OK");
    }

    @Test
    public void givenAuthProviders_whenGetPingWithNoCred_then401() {
        ResponseEntity<String> result = makeRestCallToGetPing();

        assertThat(result.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void givenAuthProviders_whenGetPingWithBadCred_then401() {
        ResponseEntity<String> result = makeRestCallToGetPing("user", "bad_password");

        assertThat(result.getStatusCodeValue()).isEqualTo(401);
    }

    private ResponseEntity<String> makeRestCallToGetPing(String username, String password) {
        return restTemplate.withBasicAuth(username, password)
            .getForEntity("/api/ping", String.class, Collections.emptyMap());
    }

    private ResponseEntity<String> makeRestCallToGetPing() {
        return restTemplate.getForEntity("/api/ping", String.class, Collections.emptyMap());
    }
}
