package com.baeldung.actuator;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.LocalManagementPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "management.port=0")
public class LoginServiceIntegrationTest {

    @LocalManagementPort
    private int port;

    @Autowired
    private LoginServiceImpl loginService;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenLoginIsAdmin_thenSuccessCounterIsIncremented() throws IOException {
        boolean success = loginService.login("admin", "secret".toCharArray());
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + port + "/metrics", String.class);
        Map<String, Object> response = objectMapper.readValue(entity.getBody(), new TypeReference<HashMap<String, Object>>() {
        });

        assertThat(success, is(true));
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(response, hasEntry("counter.login.success", 1));
    }

    @Test
    public void whenLoginIsNotAdmin_thenFailureCounterIsIncremented() throws IOException {
        boolean success = loginService.login("user", "notsecret".toCharArray());
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + port + "/metrics", String.class);
        Map<String, Object> response = objectMapper.readValue(entity.getBody(), new TypeReference<HashMap<String, Object>>() {
        });

        assertThat(success, is(false));
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(response, hasEntry("counter.login.failure", 1));
    }
}
