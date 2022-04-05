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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "management.port=0")
public class CustomEndpointIntegrationTest {

    @LocalManagementPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void whenSpringContextIsBootstrapped_thenActuatorCustomEndpointWorks() throws IOException {
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:" + port + "/customEndpoint", String.class);

        assertThat(entity.getStatusCode(), is(HttpStatus.OK));

        List<String> response = objectMapper.readValue(entity.getBody(), new TypeReference<ArrayList<String>>() {
        });

        assertThat(response, hasItems("This is message 1", "This is message 2"));
    }
}
