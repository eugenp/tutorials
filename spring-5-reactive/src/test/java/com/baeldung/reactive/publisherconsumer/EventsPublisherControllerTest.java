package com.baeldung.reactive.publisherconsumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventsPublisherApplication.class)
public class EventsPublisherControllerTest {

    @Test
    public void whenStreamIsStarted_thenReceiveResponsesWithCorrectLength() {
        WebTestClient client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:8080")
                .build();

        FluxExchangeResult<String> response = client
                .get().uri("/uuids")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class);

        StepVerifier.create(response.getResponseBody())
                .expectNextCount(3)
                .expectNextMatches(uuid -> uuid.length() == 36)
                .thenCancel().verify();
    }
}