package com.baeldung.spring.rsocket.client;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

import com.baeldung.spring.rsocket.model.MarketData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketRequester.RequestSpec;
import org.springframework.messaging.rsocket.RSocketRequester.ResponseSpec;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest(value = MarketDataRestController.class)
public class MarketDataRestControllerIntegrationTest {

    @Autowired
    private WebTestClient testClient;

    @MockBean
    private RSocketRequester rSocketRequester;

    @Mock
    private RequestSpec requestSpec;

    @Test
    public void whenInitiatesRequest_ThenGetsResponse() throws Exception {
        when(rSocketRequester.route("currentMarketData")).thenReturn(requestSpec);
        when(requestSpec.data(any())).thenReturn(requestSpec);
        MarketData marketData = new MarketData("X", 1);
        when(requestSpec.retrieveMono(MarketData.class)).thenReturn(Mono.just(marketData));

        testClient.get()
                  .uri("/current/{stock}", "X")
                  .exchange()
                  .expectStatus()
                  .isOk()
                  .expectBody(MarketData.class)
                  .isEqualTo(marketData);
    }

    @Test
    public void whenInitiatesFireAndForget_ThenGetsNoResponse() throws Exception {
        when(rSocketRequester.route("collectMarketData")).thenReturn(requestSpec);
        when(requestSpec.data(any())).thenReturn(requestSpec);
        when(requestSpec.send()).thenReturn(Mono.empty());

        testClient.get()
                  .uri("/collect")
                  .exchange()
                  .expectStatus()
                  .isOk()
                  .expectBody(Void.class);
    }

    @Test
    public void whenInitiatesRequest_ThenGetsStream() throws Exception {
        when(rSocketRequester.route("feedMarketData")).thenReturn(requestSpec);
        when(requestSpec.data(any())).thenReturn(requestSpec);
        MarketData firstMarketData = new MarketData("X", 1);
        MarketData secondMarketData = new MarketData("X", 2);
        when(requestSpec.retrieveFlux(MarketData.class)).thenReturn(Flux.just(firstMarketData, secondMarketData));

        FluxExchangeResult<MarketData> result = testClient.get()
                                                          .uri("/feed/{stock}", "X")
                                                          .accept(TEXT_EVENT_STREAM)
                                                          .exchange()
                                                          .expectStatus()
                                                          .isOk()
                                                          .returnResult(MarketData.class);
        Flux<MarketData> marketDataFlux = result.getResponseBody();
        StepVerifier.create(marketDataFlux)
                    .expectNext(firstMarketData)
                    .expectNext(secondMarketData)
                    .thenCancel()
                    .verify();
    }
}
