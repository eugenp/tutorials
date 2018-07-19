package com.baeldung.reactive.event.streaming;

import java.nio.charset.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebfluxEventControllerTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void whenGetStream_thenReturns200() {
        testClient.get()
            .uri("/events")
            .accept(MediaType.TEXT_EVENT_STREAM)
            .acceptCharset(Charset.forName("UTF-8"))
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentType(MediaType.parseMediaType("text/event-stream;charset=UTF-8"));
    }
}
