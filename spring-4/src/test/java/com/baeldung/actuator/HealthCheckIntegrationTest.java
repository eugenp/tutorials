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
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsMapContaining.hasKey;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "management.port=0")
public class HealthCheckIntegrationTest {

    @LocalManagementPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenSpringContextIsBootstrapped_thenActuatorHealthEndpointWorks() throws IOException {
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + port + "/health", String.class);

        assertThat(entity.getStatusCode(), is(HttpStatus.OK));

        Map<String, Object> response = objectMapper.readValue(entity.getBody(), new TypeReference<LinkedHashMap<String, Object>>() {
        });

        assertThat(response, hasEntry("status", "UP"));
        assertThat(response, hasKey("myHealthCheck"));
        assertThat(response, hasKey("diskSpace"));
    }
}
