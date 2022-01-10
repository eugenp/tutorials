package com.baeldung.reactive.webclient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WebClientApplication.class)
public class WebControllerIntegrationTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private WebController webController;

    @Before
    public void setup() {
        webController.setServerPort(randomServerPort);
    }

    @Test
    public void whenEndpointWithBlockingClientIsCalled_thenThreeTweetsAreReceived() {
        testClient.get()
          .uri("/tweets-blocking")
          .exchange()
          .expectStatus()
          .isOk()
          .expectBodyList(Tweet.class)
          .hasSize(3);
    }

    @Test
    public void whenEndpointWithNonBlockingClientIsCalled_thenThreeTweetsAreReceived() {
        testClient.get()
          .uri("/tweets-non-blocking")
          .exchange()
          .expectStatus()
          .isOk()
          .expectBodyList(Tweet.class)
          .hasSize(3);
    }
}