package com.baeldung.springwebflux.controller;

import com.baeldung.springwebflux.main.SpringWebFluxDemoApplication;
import com.baeldung.springwebflux.model.Equity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringWebFluxDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EquityPriceBroadcastControllerTest {

    @Autowired
    private ApplicationContext context;

    private WebTestClient testClient;

    @Before
    public void init() {
        testClient = WebTestClient.bindToApplicationContext(context)
            .configureClient()
            .baseUrl("http://localhost:8080")
            .responseTimeout(Duration.ofSeconds(30))
            .build();
    }

    @Test
    public void testBroadcastEquityPrices() {

        FluxExchangeResult<Equity> result = this.testClient.get()
            .uri("/api/getEquityPrice")
            .accept(MediaType.APPLICATION_STREAM_JSON)
            .exchange()
            .expectStatus()
            .isOk()
            .returnResult(Equity.class);

        Flux<Equity> resultBody = result.getResponseBody();
        StepVerifier.create(resultBody)
            .consumeNextWith(equity -> {
                Assert.assertTrue(equity != null && equity.getEquityId() > -1 && equity.getEquityPrice() > -1);
            })
            .thenCancel()
            .verify();
    }
}