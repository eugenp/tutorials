package com.baeldung.requesttimeout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RequestTimeoutIntegrationTest {

    private static final WebClient WEB_CLIENT = WebClient.builder().baseUrl("http://localhost:8080").build();

    @Test(expected = WebClientRequestException.class)
    public void givenTransactionTimeout_whenTimeExpires_thenReceiveException() {
        getAuthor("transactional");
    }

    @Test(expected = WebClientRequestException.class)
    public void givenResilience4jTimeLimiter_whenTimeExpires_thenReceiveException() {
        getAuthor("resilience4j");
    }

    @Test(expected = WebClientRequestException.class)
    public void givenMvcRequestTimeout_whenTimeExpires_thenReceiveException() {
        getAuthor("mvc-request-timeout");
    }

    @Test(expected = WebClientRequestException.class)
    public void givenWebClientTimeout_whenTimeExpires_thenReceiveException() {
        getAuthor("webclient");
    }

    private void getAuthor(String authorPath) {
        WEB_CLIENT.get()
          .uri(uriBuilder -> uriBuilder
             .path("/author/" + authorPath)
             .queryParam("title", "title")
             .build())
          .retrieve()
          .bodyToMono(String.class)
          .block();
    }
}
