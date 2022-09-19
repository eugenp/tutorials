package com.baeldung.webclient;

import com.baeldung.webclient.status.WebClientStatusCodeHandler;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
public class WebClientStatusCodeHandlerIntegrationTest {
    private String baseUrl;
    private WireMockServer wireMockServer;

    @Before
    public void setUp() {
        wireMockServer = new WireMockServer(wireMockConfig().dynamicPort());
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        baseUrl = format("http://localhost:%s", wireMockServer.port());
    }

    @After
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void whenResponseIs2XX_thenBothStatusHandlerAndExchangeFilterReturnEqualResponses() {
        stubPostResponse("/success", 200, "success");

        Mono<String> responseStatusHandler = WebClientStatusCodeHandler
            .getResponseBodyUsingOnStatus(baseUrl + "/success");

        Mono<String> responseExchangeFilter = WebClientStatusCodeHandler
           .getResponseBodyUsingExchangeFilterFunction(baseUrl + "/success");

        assertThat(responseStatusHandler.block())
            .isEqualTo(responseExchangeFilter.block())
            .isEqualTo("success");
    }

    @Test
    public void whenResponseIs500_thenBothStatusHandlerAndExchangeFilterReturnEqualResponses() {
        stubPostResponse("/server-error", 500, "Internal Server Error");

        Mono<String> responseStatusHandler = WebClientStatusCodeHandler
            .getResponseBodyUsingOnStatus(baseUrl + "/server-error");

        Mono<String> responseExchangeFilter = WebClientStatusCodeHandler
            .getResponseBodyUsingExchangeFilterFunction(baseUrl + "/server-error");

        assertThatThrownBy(responseStatusHandler::block)
            .isInstanceOf(Exception.class)
            .hasMessageContaining("Internal Server Error");

        assertThatThrownBy(responseExchangeFilter::block)
            .isInstanceOf(Exception.class)
            .hasMessageContaining("Internal Server Error");
    }

    @Test
    public void whenResponseIs400_thenBothStatusHandlerAndExchangeFilterReturnEqualResponses() {
        stubPostResponse("/client-error", 400, "Bad Request");

        Mono<String> responseStatusHandler = WebClientStatusCodeHandler
            .getResponseBodyUsingOnStatus(baseUrl + "/client-error");

        Mono<String> responseExchangeFilter = WebClientStatusCodeHandler
            .getResponseBodyUsingExchangeFilterFunction(baseUrl + "/client-error");

        assertThatThrownBy(responseStatusHandler::block)
            .isInstanceOf(Exception.class)
            .hasMessageContaining("Bad Request");

        assertThatThrownBy(responseExchangeFilter::block)
            .isInstanceOf(Exception.class)
            .hasMessageContaining("Bad Request");
    }

    private static void stubPostResponse(String url, int statusCode, String response) {
        stubFor(post(urlEqualTo(url)).willReturn(aResponse()
            .withStatus(statusCode)
            .withBody(response)));
    }
}