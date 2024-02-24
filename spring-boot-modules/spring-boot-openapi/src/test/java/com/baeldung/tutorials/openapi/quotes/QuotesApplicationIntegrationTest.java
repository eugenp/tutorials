package com.baeldung.tutorials.openapi.quotes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;

import com.baeldung.tutorials.openapi.quotes.api.model.QuoteResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuotesApplicationIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void whenGetQuoteMultipleTimes_thenResponseCached() {

        // Call server a few times and collect responses
        var quotes = IntStream.range(1, 10).boxed()
          .map((i) -> restTemplate.getForEntity("http://localhost:" + port + "/quotes/BAEL", QuoteResponse.class))
          .map(HttpEntity::getBody)
          .collect(Collectors.groupingBy((q -> q.hashCode()), Collectors.counting()));

        assertThat(quotes.size()).isEqualTo(1);



    }


}