package com.baeldung.cloud.openfeign.getbody;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetBodyFeignClientIntegrationTest {

    private static WireMockServer wireMockServer;

    @Autowired
    private GetBodyFeignClient getBodyFeignClient;

    @BeforeAll
    static void setupWireMock() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();

        WireMock.configureFor("localhost", 8080);

        // Stub endpoint
        WireMock.stubFor(WireMock.get(WireMock.urlMatching("/api/search.*"))
            .willReturn(WireMock.aResponse()
                .withStatus(200)
                .withBody("GET request received")));

    }

    @AfterAll
    static void stopWireMock() {
        wireMockServer.stop();
    }

    @Test
    void givenRequestBody_whenCallGetHTTPMethod_returnException() {
        SearchRequest request = new SearchRequest();
        request.setKeyword("spring");
        request.setCategory("tutorial");

        Assertions.assertThrows(feign.FeignException.class, () -> {
            getBodyFeignClient.search(request);
        });
    }

    @Test
    void givenRequestBody_whenUsingSpringQueryMap_thenRequestSucceeds() {
        SearchRequest request = new SearchRequest();
        request.setKeyword("spring");
        request.setCategory("tutorial");

        getBodyFeignClient.searchWithSpringQueryMap(request);

        WireMock.verify(WireMock.getRequestedFor(WireMock.urlEqualTo("/api/search?keyword=spring&category=tutorial")));
    }
}
