package com.baeldung.webflux.responsestatus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResponseStatusControllerLiveTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void whenCallRest_thenStatusIsOk() {
        testClient.get()
                .uri("/statuses/ok")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void whenCallRest_thenStatusIsNoContent() {
        testClient.get()
                .uri("/statuses/no-content")
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    public void whenCallRest_thenStatusIsAccepted() {
        testClient.get()
                .uri("/statuses/accepted")
                .exchange()
                .expectStatus()
                .isAccepted();
    }

    @Test
    public void whenCallRest_thenStatusIsBadRequest() {
        testClient.get()
                .uri("/statuses/bad-request")
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    public void whenCallRest_thenStatusIsUnauthorized() {
        testClient.get()
                .uri("/statuses/unauthorized")
                .exchange()
                .expectStatus()
                .isUnauthorized();
    }

    @Test
    public void whenCallRest_thenStatusIsNotFound() {
        testClient.get()
                .uri("/statuses/not-found")
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
