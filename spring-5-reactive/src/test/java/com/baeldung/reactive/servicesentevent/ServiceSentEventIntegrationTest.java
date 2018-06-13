package com.baeldung.reactive.servicesentevent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceSentEventIntegrationTest {

    @Test
    public void whenEndpointIsCalled_thenEventStreamingBegins() {

        Executable sseStreamingCall = () -> WebTestClient.bindToServer()
            .baseUrl("http://localhost:8080")
            .build()
            .get()
            .uri("/sse/stream")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
            .expectBody(String.class);

        Assertions.assertThrows(IllegalStateException.class, sseStreamingCall, "Expected test to timeout and throw IllegalStateException, but it didn't");
    }
}
