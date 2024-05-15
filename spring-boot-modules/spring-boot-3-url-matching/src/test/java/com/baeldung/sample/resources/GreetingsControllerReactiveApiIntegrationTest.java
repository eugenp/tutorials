package com.baeldung.sample.resources;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebFluxTest
public class GreetingsControllerReactiveApiIntegrationTest {
    private static final String BASEURL = "/some/reactive";
    @Autowired
    private WebTestClient webClient;
    @Test
    public void testGreeting() {
        webClient.get().uri( BASEURL + "/greeting")
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(result -> {
                    String responseBody = new String(result.getResponseBody());
                    assertTrue(responseBody.contains("Hello reactive"));
                });
    }
    @Test
    @Disabled("Disabled while TrailingSlashRedirectFilter is in use.")
    public void testGreetingTrailingSlash() {
        webClient.get().uri(BASEURL + "/greeting/")
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(result -> {
                    String responseBody = new String(result.getResponseBody());
                    assertTrue(responseBody.contains("Hello with slash reactive"));
                });
    }
    @Test
    public void testGreetingTrailingSlashWithFilter() {
        webClient.get().uri(BASEURL +  "/greeting/")
                .exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith(result -> {
                    String responseBody = new String(result.getResponseBody());
                    assertTrue(responseBody.contains("Hello reactive"));
                });
    }
}
