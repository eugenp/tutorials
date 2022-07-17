package com.baeldung.jersey;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.TEXT_HTML;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(properties = "spring.security.oauth2.client.registration.github.client-id:test-id")
public class JerseyResourceUnitTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String basePath;

    @Before
    public void setup() {
        basePath = "http://localhost:" + port + "/";
    }

    @Test
    public void whenUserIsUnauthenticated_thenTheyAreRedirectedToLoginPage() {
        ResponseEntity<Object> response = restTemplate.getForEntity(basePath, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getBody()).isNull();

        URI redirectLocation = response.getHeaders().getLocation();
        assertThat(redirectLocation).isNotNull();
        assertThat(redirectLocation.toString()).isEqualTo(basePath + "login");
    }

    @Test
    public void whenUserAttemptsToLogin_thenAuthorizationPathIsReturned() {
        ResponseEntity<String> response = restTemplate.getForEntity(basePath + "login", String.class);
        assertThat(response.getHeaders().getContentType()).isEqualTo(TEXT_HTML);
        assertThat(response.getBody()).isEqualTo("Log in with <a href=\"/oauth2/authorization/github\">GitHub</a>");
    }

    @Test
    public void whenUserAccessesAuthorizationEndpoint_thenTheyAresRedirectedToProvider() {
        ResponseEntity<String> response = restTemplate.getForEntity(basePath + "oauth2/authorization/github", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(response.getBody()).isNull();

        URI redirectLocation = response.getHeaders().getLocation();
        assertThat(redirectLocation).isNotNull();
        assertThat(redirectLocation.getHost()).isEqualTo("github.com");
        assertThat(redirectLocation.getPath()).isEqualTo("/login/oauth/authorize");

        String redirectionQuery = redirectLocation.getQuery();
        assertThat(redirectionQuery.contains("response_type=code"));
        assertThat(redirectionQuery.contains("client_id=test-id"));
        assertThat(redirectionQuery.contains("scope=read:user"));
    }
}
