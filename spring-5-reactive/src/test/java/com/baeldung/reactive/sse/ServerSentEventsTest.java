package com.baeldung.reactive.sse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerSentEventsTest {

    @Autowired
    private WebTestClient webClient;

    @Test
    public void contextLoads() {
    }

    @Test
    public void givenValidRequest_shouldReceiveOk() throws Exception {

        webClient.get().uri("/events").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void givenInvalidHttpVerb_shouldReceiveMethodNotAllowedError() throws Exception {

        webClient.post().uri("/events").accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
    }

}
