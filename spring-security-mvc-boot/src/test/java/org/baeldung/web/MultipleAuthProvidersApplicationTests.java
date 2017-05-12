package org.baeldung.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;

import org.baeldung.multipleauthproviders.MultipleAuthProvidersApplication;
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
public class MultipleAuthProvidersApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenDbWithUsers_whenGetPingWithValidUser_thenOk() {
        ResponseEntity<String> result = makeRestCallToGetPing("dbuser", "pass");

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("OK");
    }

    @Test
    public void givenDbWithUsers_whenGetPingWithInsufficientRole_then403() {
        ResponseEntity<String> result = makeRestCallToGetPing("dbguest", "guest");

        assertThat(result.getStatusCodeValue()).isEqualTo(403);
        assertThat(result.getBody()).contains("Access is denied");
    }

    @Test
    public void givenLDAPWithUsers_whenGetPingWithValidUser_thenOK() {
        ResponseEntity<String> result = makeRestCallToGetPing("ldapuser", "pass");

        assertThat(result.getStatusCodeValue()).isEqualTo(200);
        assertThat(result.getBody()).isEqualTo("OK");
    }

    @Test
    public void givenLDAPWithUsers_whenGetPingWithInsufficientRole_then403() {
        ResponseEntity<String> result = makeRestCallToGetPing("ldapguest", "pass");

        assertThat(result.getStatusCodeValue()).isEqualTo(403);
        assertThat(result.getBody()).contains("Access is denied");
    }

    @Test
    public void givenAuthProviders_whenGetPingWithNoCred_then401() {
        ResponseEntity<String> result = makeRestCallToGetPing();

        assertThat(result.getStatusCodeValue()).isEqualTo(401);
        assertThat(result.getBody()).contains("Full authentication is required to access this resource");
    }

    @Test
    public void givenAuthProviders_whenGetPingWithBadCred_then401() {
        ResponseEntity<String> result = makeRestCallToGetPing("user", "bad_password");
        assertThat(result.getStatusCodeValue()).isEqualTo(401);
        assertThat(result.getBody()).contains("Bad credentials");
    }

    private ResponseEntity<String> makeRestCallToGetPing(String username, String password) {
        return restTemplate.withBasicAuth(username, password)
            .getForEntity("/api/ping", String.class, Collections.emptyMap());
    }

    private ResponseEntity<String> makeRestCallToGetPing() {
        return restTemplate.getForEntity("/api/ping", String.class, Collections.emptyMap());
    }
}
