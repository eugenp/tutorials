package com.baeldung.springbootsecurityrest;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import com.baeldung.springbootsecuritycors.basicauth.SpringBootSecurityApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SpringBootSecurityApplication.class)
public class BasicAuthConfigurationIntegrationTest {

    @Test
    public void givenCredentials_whenRequested_thenLogin() throws IllegalStateException, IOException, RestClientException, URISyntaxException {
        TestRestTemplate restTemplate = new TestRestTemplate();
        URL base = new URL("http://192.168.1.101:8082/user");
        ResponseEntity<String> response = restTemplate.withBasicAuth("user", "password").postForEntity(base.toURI(), null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
}
