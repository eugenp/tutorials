package com.baeldung.customlogouthandler;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.customlogouthandler.services.UserCache;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { CustomLogoutApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({ @Sql(value = "classpath:customlogouthandler/before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD), @Sql(value = "classpath:customlogouthandler/after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) })
@TestPropertySource(locations="classpath:customlogouthandler/application.properties")
class CustomLogoutHandlerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserCache userCache;

    @LocalServerPort
    private int port;

    @Test
    public void whenLogin_thenUseUserCache() {
        // User cache should be empty on start
        assertThat(userCache.size()).isEqualTo(0);

        // Request using first login
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "pass")
            .getForEntity(getLanguageUrl(), String.class);

        assertThat(response.getBody()).contains("english");

        // User cache must contain the user
        assertThat(userCache.size()).isEqualTo(1);

        // Getting the session cookie
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", response.getHeaders()
            .getFirst(HttpHeaders.SET_COOKIE));

        // Request with the session cookie
        response = restTemplate.exchange(getLanguageUrl(), HttpMethod.GET, new HttpEntity<String>(requestHeaders), String.class);
        assertThat(response.getBody()).contains("english");

        // Logging out using the session cookies
        response = restTemplate.exchange(getLogoutUrl(), HttpMethod.GET, new HttpEntity<String>(requestHeaders), String.class);
        assertThat(response.getStatusCode()
            .value()).isEqualTo(200);
    }

    @Test
    public void whenLogout_thenCacheIsEmpty() {
        // User cache should be empty on start
        assertThat(userCache.size()).isEqualTo(0);

        // Request using first login
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "pass")
            .getForEntity(getLanguageUrl(), String.class);

        assertThat(response.getBody()).contains("english");

        // User cache must contain the user
        assertThat(userCache.size()).isEqualTo(1);

        // Getting the session cookie
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", response.getHeaders()
            .getFirst(HttpHeaders.SET_COOKIE));

        // Logging out using the session cookies
        response = restTemplate.exchange(getLogoutUrl(), HttpMethod.GET, new HttpEntity<String>(requestHeaders), String.class);
        assertThat(response.getStatusCode()
            .value()).isEqualTo(200);

        // User cache must be empty now
        // this is the reaction on custom logout filter execution
        assertThat(userCache.size()).isEqualTo(0);

        // Assert unauthorized request
        response = restTemplate.exchange(getLanguageUrl(), HttpMethod.GET, new HttpEntity<String>(requestHeaders), String.class);
        assertThat(response.getStatusCode()
            .value()).isEqualTo(401);
    }

    private String getLanguageUrl() {
        return "http://localhost:" + port + "/user/language";
    }

    private String getLogoutUrl() {
        return "http://localhost:" + port + "/user/logout";
    }

}
