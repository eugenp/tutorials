package com.baeldung.reactive.controller;

import com.baeldung.reactive.Spring5ReactiveApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Spring5ReactiveApplication.class)
public class CpuUsageEventProducerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void whenGetCpuUsageEvents_thenReturns200() {
        webTestClient.get()
                .uri("/events/stream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.cpuUsage").isNotEmpty()
                .jsonPath("$.time").isNotEmpty();
    }

}
