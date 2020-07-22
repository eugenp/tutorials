package com.baeldung.logoutredirects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LogoutApplicationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void whenLogout_thenDisableRedirect() {

        ResponseEntity<String> response = restTemplate.getForEntity(getLogoutUrl(), String.class);
        assertThat(response.getStatusCode()
            .value()).isEqualTo(200);
        assertThat(response.getBody()).isNullOrEmpty();
    }

    private String getLogoutUrl() {
        return "http://localhost:" + port + "/logout";
    }

}
