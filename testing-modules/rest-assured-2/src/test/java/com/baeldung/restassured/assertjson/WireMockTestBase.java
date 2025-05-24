package com.baeldung.restassured.assertjson;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest
abstract class WireMockTestBase {

    @BeforeEach
    void beforeEach(WireMockRuntimeInfo wmRuntimeInfo) {
        stubFor(get("/static").willReturn(
            aResponse()
                .withStatus(200)
                .withHeader("content-type", "application/json")
                .withBody("{\"name\":\"baeldung\",\"type\":\"website\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}")));

        stubFor(get("/build").willReturn(
                aResponse()
                        .withStatus(200)
                        .withHeader("content-type", "application/json")
                        .withBody("{\"build\":\"" + UUID.randomUUID() + "\",\"timestamp\":\"" + LocalDateTime.now() + "\",\"name\":\"baeldung\",\"type\":\"website\",\"text\":{\"language\":\"english\",\"code\":\"java\"}}")));

        RestAssured.port = wmRuntimeInfo.getHttpPort();
    }

}
