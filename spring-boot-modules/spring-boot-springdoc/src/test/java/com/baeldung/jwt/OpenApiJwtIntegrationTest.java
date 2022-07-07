package com.baeldung.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("OpenAPI JWT Live Tests")
class OpenApiJwtIntegrationTest
{
    @LocalServerPort
    private int port;
    @Autowired
    private AuthenticationApi authenticationApi;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("LiveTest - Render Swagger UI")
    void whenInvokeSwagger_thenRenderIndexPage()
    {
        assertNotNull(authenticationApi);

        String response = this.restTemplate.getForObject("http://localhost:" + port + "/swagger-ui/index.html", String.class);

        assertNotNull(response);
        assertTrue(response.contains("Swagger UI"));
        assertTrue(response.contains("<div id=\"swagger-ui\"></div>"));
    }

    @Test
    @DisplayName("LiveTest - Check Headers")
    void whenInvokeOpenApi_thenCheckHeaders()
    {
        assertNotNull(authenticationApi);

        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/api-docs", String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Content-Type"));
        assertEquals(1, response.getHeaders().get("Content-Type").size());
        assertEquals("application/json", response.getHeaders().get("Content-Type").get(0));
    }

    @Test
    @DisplayName("LiveTest - Verify OpenAPI Document")
    void whenInvokeOpenApi_thenVerifyOpenApiDoc()
    {
        assertNotNull(authenticationApi);


        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/api-docs", String.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("\"openapi\":"));
        assertTrue(response.getBody().contains("User API"));
        assertTrue(response.getBody().contains("\"post\""));
    }

    @Test
    @DisplayName("LiveTest - Verify OpenAPI Security Section")
    void whenInvokeOpenApi_thenCheckSecurityConfig()
    {
        assertNotNull(authenticationApi);

        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/api-docs", String.class);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("\"securitySchemes\""));
        assertTrue(response.getBody().contains("\"bearerFormat\":\"JWT\""));
        assertTrue(response.getBody().contains("\"scheme\":\"bearer\""));
    }

}

