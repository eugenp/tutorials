package com.baeldung.resttemplate.methods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;

/**
 * Unit tests for different ways to send POST with RestTemplate.
 */
public class RestTemplateMethodsUnitTest
{

    private final RestTemplate restTemplate = new RestTemplate();

    private final String URL = "https://localhost:8080";

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    /**
     * Test that postForEntity sends a POST to the desired URL.
     */
    @Test
    public void testPostForEntity() {

        mockServer.expect(requestTo(URL))
                .andExpect(method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body("Ok"));

        restTemplate.postForEntity(
                URL,
                "Test Body",
                String.class);

        mockServer.verify();
    }

    /**
     * Test that exchange with POST method sends a POST to the desired URL.
     */
    @Test
    public void testPostExchange() {
        mockServer.expect(requestTo(URL))
                .andExpect(method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body("Ok"));

        restTemplate.exchange(
                URL,
                HttpMethod.POST,
                new HttpEntity<>("Test Body"),
                String.class);

        mockServer.verify();
    }

    @Test
    public void testPostExecute() {
        mockServer.expect(requestTo(URL))
                .andExpect(method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                        .contentType(MediaType.TEXT_PLAIN)
                        .body("Ok"));

        restTemplate.execute(
                URL,
                HttpMethod.POST,
                restTemplate.httpEntityCallback("Test body"),
                restTemplate.responseEntityExtractor(String.class));

        mockServer.verify();
    }
}
