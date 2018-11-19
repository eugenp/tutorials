package com.baeldung.json;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public abstract class JsonEscapeApplicationBaseIntegrationTest {

    private static WebTestClient client;

    @Rule
    public WireMockRule wireMock = new WireMockRule(options().port(8081));

    @BeforeClass
    public static void setup() throws Exception {
        client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Test
    public void givenRouter_whenPost_thenGotAccepted_PayloadEscaped() throws Exception {
        stubFor(post(urlMatching(".*/check")).willReturn(aResponse().withStatus(HttpStatus.SC_OK)));

        client.post()
                .uri("/").body(BodyInserters.fromFormData("input", "b \"a\""))
                .exchange()
                .expectStatus()
                .isAccepted();

        verify(postRequestedFor(urlMatching(".*/check")).withRequestBody(containing("\"message\":\"b \\\"a\\\"\"")));
    }

}