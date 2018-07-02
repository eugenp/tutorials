package com.baeldung.spring.webflux.eventstreaming;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.spring.webflux.eventstreaming.model.Event;
import com.baeldung.spring.webflux.eventstreaming.server.EventStreamingServerApplication;

import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = EventStreamingServerApplication.class)
public class EventStreamingServerApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGet() throws Exception {
        FluxExchangeResult<Event> eventStreams = webTestClient.get()
            .uri("/events")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
            .returnResult(Event.class);

        StepVerifier.create(eventStreams.getResponseBody())
            .consumeNextWith(event -> assertEquals(event.getId(), 1l))
            .expectNextCount(4)
            .thenCancel()
            .verify();
    }

}
