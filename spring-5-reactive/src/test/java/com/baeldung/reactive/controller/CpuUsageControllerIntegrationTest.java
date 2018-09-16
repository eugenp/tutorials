package com.baeldung.reactive.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.reactive.Spring5ReactiveApplication;
import com.baeldung.reactive.model.CpuUsage;

import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Spring5ReactiveApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CpuUsageControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    public void whenEndpointIsCalled_thenCpuUsageFluxIsReturned() {        
        FluxExchangeResult<CpuUsage> result = WebTestClient.bindToServer()
            .baseUrl("http://localhost:" + port)
            .build()
            .get()
            .uri("/cpu-usage")
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader()
            .contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
            .returnResult(CpuUsage.class);

        StepVerifier.create(result.getResponseBody())
            .consumeNextWith(cpuUsage -> assertThat(cpuUsage.getUsage()).isGreaterThanOrEqualTo(0))
            .thenCancel()
            .verify();
    }

}
