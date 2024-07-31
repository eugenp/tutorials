package com.baeldung.cloud.openfeign.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.cloud.openfeign.defaulterrorhandling.model.FormData;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class FormClientIntegrationTest {

    private static WireMockServer wireMockServer;

    @Autowired
    FormClient formClient;

    @BeforeAll
    public static void startWireMockServer() {
        wireMockServer = new WireMockServer(8085);
        configureFor("localhost", 8085);
        wireMockServer.start();

    }

    @AfterAll
    public static void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    public void givenFormData_whenPostFormDataCalled_thenReturnSuccess() {
        FormData formData = new FormData(1, "baeldung");
        stubFor(WireMock.post(urlEqualTo("/api/form"))
          .willReturn(aResponse().withStatus(HttpStatus.OK.value())));

        formClient.postFormData(formData);
        wireMockServer.verify(postRequestedFor(urlPathEqualTo("/api/form"))
          .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded; charset=UTF-8"))
          .withRequestBody(equalTo("name=baeldung&id=1")));
    }

    @Test
    public void givenFormMap_whenPostFormMapDataCalled_thenReturnSuccess() {
        Map<String, String> mapData = new HashMap<>();
        mapData.put("name", "baeldung");
        mapData.put("id", "1");
        stubFor(WireMock.post(urlEqualTo("/api/form/map"))
          .willReturn(aResponse().withStatus(HttpStatus.OK.value())));

        formClient.postFormMapData(mapData);
        wireMockServer.verify(postRequestedFor(urlPathEqualTo("/api/form/map"))
          .withHeader("Content-Type", equalTo("application/x-www-form-urlencoded; charset=UTF-8"))
          .withRequestBody(equalTo("name=baeldung&id=1")));
    }

}