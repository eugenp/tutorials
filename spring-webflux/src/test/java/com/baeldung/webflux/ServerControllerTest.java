package com.baeldung.webflux;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void fetchQuotesAsStream() {
        List<String> result = webTestClient.get()
            .uri("/text")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .exchange()
            .returnResult(String.class)
            .getResponseBody()
            .take(10)
            .collectList()
            .block();

        assertThat(result).allSatisfy(data -> assertThat(data.toString()).isNotBlank());
    }

}
