package com.baeldung.forwardheaders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerLiveTest {

    private RestTestClient restTestClient;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTestClient = RestTestClient
            .bindToServer()
            .baseUrl("http://localhost:" + port)
            .build();
    }

    @Test
    void givenTestApiIsRunning_whenGetTestIsCalled_thenReturnOkWithNoContent() {
        restTestClient.get()
            .uri("/test")
            .exchange()
            .expectStatus().isOk()
            .expectBody().isEmpty();
    }
}
