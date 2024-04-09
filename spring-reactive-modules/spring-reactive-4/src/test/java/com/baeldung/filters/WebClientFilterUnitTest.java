package com.baeldung.filters;

import static com.baeldung.filters.WebClientFilters.modifyRequestHeaders;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@WireMockTest
public class WebClientFilterUnitTest {

    @RegisterExtension
    static WireMockExtension extension = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort()
            .dynamicHttpsPort())
        .build();

    @Test
    void whenCallEndpoint_thenRequestHeadersModified() {
        extension.stubFor(get("/test").willReturn(aResponse().withStatus(200)
            .withBody("SUCCESS")));

        final MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        WebClient webClient = WebClient.builder()
            .filter(modifyRequestHeaders(map))
            .build();
        String actual = sendGetRequest(webClient);

        final String body = "SUCCESS";
        Assertions.assertEquals(actual, body);
        Assertions.assertEquals("TRACE-ID", map.getFirst("traceId"));
    }

    private String sendGetRequest(final WebClient webClient) {
        return webClient.get()
            .uri(getUrl())
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    private String getUrl() {
        return "http://localhost:" + extension.getPort() + "/test";
    }
}
