package com.baeldung.requesttimeout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RequestTimeoutIntegrationTest {

    @LocalServerPort
    private int port;

    private WebClient webClient;

    @Before
    public void setUp() {
        webClient = WebClient.builder()
          .baseUrl("http://localhost:" + port)
          .build();
    }

    @Test(expected = WebClientResponseException.InternalServerError.class)
    public void givenTransactionTimeout_whenTimeExpires_thenReceiveException() {
        getAuthor("transactional");
    }

    @Test(expected = WebClientResponseException.InternalServerError.class)
    public void givenResilience4jTimeLimiter_whenTimeExpires_thenReceiveException() {
        getAuthor("resilience4j");
    }

    @Test(expected = WebClientResponseException.ServiceUnavailable.class)
    public void givenMvcRequestTimeout_whenTimeExpires_thenReceiveException() {
        getAuthor("mvc-request-timeout");
    }

    @Test(expected = WebClientResponseException.InternalServerError.class)
    public void givenWebClientTimeout_whenTimeExpires_thenReceiveException() {
        getAuthor("webclient");
    }

    private void getAuthor(String authorPath) {
        webClient.get()
          .uri(uriBuilder -> uriBuilder
            .path("/author/" + authorPath)
            .queryParam("title", "title")
            .build())
          .retrieve()
          .bodyToMono(String.class)
          .block();
    }

}
