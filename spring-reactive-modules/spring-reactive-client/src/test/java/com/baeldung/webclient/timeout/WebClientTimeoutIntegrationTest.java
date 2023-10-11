package com.baeldung.webclient.timeout;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientRequest;


import java.time.Duration;
import java.util.concurrent.TimeoutException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WebClientTimeoutIntegrationTest {

    private WireMockServer wireMockServer;

    @Before
    public void setup() {
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
    }

    @After
    public void tearDown() {
        wireMockServer.stop();
    }

    @AfterEach
    public void tearDownEach() {
        wireMockServer.resetAll();
    }

    @Test
    public void givenResponseTimeoutClientWhenRequestTimeoutThenReadTimeoutException() {
        val path = "/response-timeout";
        val delay = Math.toIntExact(Duration.ofSeconds(2).toMillis());
        stubFor(get(urlEqualTo(path)).willReturn(aResponse().withFixedDelay(delay)
          .withStatus(HttpStatus.OK.value())));

        val webClient = WebClientTimeoutProvider.responseTimeoutClient();

        val ex = assertThrows(RuntimeException.class, () ->
          webClient.get()
            .uri(wireMockServer.baseUrl() + path)
            .exchangeToMono(Mono::just)
            .log()
            .block());
        assertThat(ex).isInstanceOf(WebClientRequestException.class)
          .getCause().isInstanceOf(ReadTimeoutException.class);
    }

    @Test
    public void givenReadWriteTimeoutClientWhenRequestTimeoutThenReadTimeoutException() {
        val path = "/read-write-timeout";
        val delay = Math.toIntExact(Duration.ofSeconds(6).toMillis());
        stubFor(get(urlEqualTo(path)).willReturn(aResponse().withFixedDelay(delay)
          .withStatus(HttpStatus.OK.value())));

        val webClient = WebClientTimeoutProvider.readWriteTimeoutClient();

        val ex = assertThrows(RuntimeException.class, () ->
          webClient.get()
            .uri(wireMockServer.baseUrl() + path)
            .exchangeToMono(Mono::just)
            .log()
            .block());
        assertThat(ex).isInstanceOf(WebClientRequestException.class)
          .getCause().isInstanceOf(ReadTimeoutException.class);
    }

    @Test
    public void givenNoTimeoutClientAndReactorTimeoutWhenRequestTimeoutThenTimeoutException() {
        val path = "/reactor-timeout";
        val delay = Math.toIntExact(Duration.ofSeconds(5).toMillis());
        stubFor(get(urlEqualTo(path)).willReturn(aResponse().withFixedDelay(delay)
          .withStatus(HttpStatus.OK.value())));

        val webClient = WebClientTimeoutProvider.defaultWebClient();

        val ex = assertThrows(RuntimeException.class, () ->
          webClient.get()
            .uri(wireMockServer.baseUrl() + path)
            .exchangeToMono(Mono::just)
            .timeout(Duration.ofSeconds(1))
            .log()
            .block());
        assertThat(ex).hasMessageContaining("Did not observe any item")
          .getCause().isInstanceOf(TimeoutException.class);
    }

    @Test
    public void givenNoTimeoutClientAndTimeoutHttpRequestWhenRequestTimeoutThenReadTimeoutException() {
        val path = "/reactor-http-request-timeout";
        val delay = Math.toIntExact(Duration.ofSeconds(5).toMillis());
        stubFor(get(urlEqualTo(path)).willReturn(aResponse().withFixedDelay(delay)
          .withStatus(HttpStatus.OK.value())));

        val webClient = WebClientTimeoutProvider.defaultWebClient();

        val ex = assertThrows(RuntimeException.class, () ->
          webClient.get()
            .uri(wireMockServer.baseUrl() + path)
            .httpRequest(httpRequest -> {
              HttpClientRequest reactorRequest = httpRequest.getNativeRequest();
              reactorRequest.responseTimeout(Duration.ofSeconds(1));
            })
            .exchangeToMono(Mono::just)
            .log()
            .block());
        assertThat(ex).isInstanceOf(WebClientRequestException.class)
          .getCause().isInstanceOf(ReadTimeoutException.class);
    }
}
