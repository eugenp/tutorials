package com.baeldung.basicauth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OpenAPIIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenInvokeSwagger_thenRenderIndexPage() {
        String response = this.restTemplate.getForObject("http://localhost:" + port + "/swagger-ui/index.html", String.class);

        assertNotNull(response);
        assertTrue(response.contains("Swagger UI"));
        assertTrue(response.contains("<div id=\"swagger-ui\"></div>"));
    }

    @Test
    void whenInvokeOpenApi_thenCheckHeaders() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/v3/api-docs", String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(1, response.getHeaders().get("Content-Type").size());
        assertEquals("application/json", response.getHeaders().get("Content-Type").get(0));
    }

    @Test
    void whenInvokeOpenApi_thenVerifyOpenApiDoc() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/v3/api-docs", String.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("\"openapi\":"));
        assertTrue(response.getBody().contains("Foos API"));
        assertTrue(response.getBody().contains("\"post\""));
    }

    @Test
    void whenInvokeOpenApi_thenCheckSecurityConfig() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/v3/api-docs", String.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("\"securitySchemes\""));
        assertTrue(response.getBody().contains("\"type\":\"http\""));
        assertTrue(response.getBody().contains("\"scheme\":\"basic\""));
    }
}
