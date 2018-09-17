package com.baeldung.reactive;

import com.baeldung.reactive.model.Event;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringWebfluxApplicationTests {

    @Test
    public void givenReactiveClient_whenCallEndpoint_thenReceiveMessage() {

        WebTestClient webClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:8080/api")
                .build();

        Event event = webClient.get()
                .uri("/events")
                .exchange()
                .returnResult(Event.class)
                .getResponseBody().blockFirst();

        Assert.assertNotNull(event);
    }
}
