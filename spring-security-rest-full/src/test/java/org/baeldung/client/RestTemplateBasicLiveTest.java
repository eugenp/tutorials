package org.baeldung.client;

import static org.baeldung.Consts.APPLICATION_PORT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateBasicLiveTest {

    private RestTemplate restTemplate;
    private static final String fooResourceUrl = "http://localhost:" + APPLICATION_PORT + "/spring-security-rest-full/auth/foos";

    @Before
    public void beforeTest() {
        restTemplate = new RestTemplate();
    }

    // GET

    @Test
    public void givenResourceUrl_whenSendGetForRequestEntity_thenStatusOk() throws IOException {
        final ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }
    
}
