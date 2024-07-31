package com.baeldung.cloud.openfeign.customizederrorhandling.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertThrows;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.cloud.openfeign.ExampleApplication;
import com.baeldung.cloud.openfeign.customizederrorhandling.exception.ProductNotFoundException;
import com.baeldung.cloud.openfeign.customizederrorhandling.exception.ProductServiceNotAvailableException;
import com.github.tomakehurst.wiremock.WireMockServer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class ProductClientIntegrationTest {

    @Autowired
    private ProductClient productClient;

    private WireMockServer wireMockServer;

    @Before
    public void startWireMockServer() {
        wireMockServer = new WireMockServer(8088);
        configureFor("localhost", 8088);
        wireMockServer.start();
    }

    @After
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    public void givenProductApiIsNotAvailable_whenGetProductCalled_thenThrowProductServiceNotAvailableException() {
        String productId = "test";

        stubFor(get(urlEqualTo("/product/" + productId))
                .willReturn(aResponse().withStatus(503)));

        assertThrows(ProductServiceNotAvailableException.class, () -> productClient.getProduct(productId));
    }

    @Test
    public void givenProductNotFound_whenGetProductCalled_thenThrowBadRequestException() {
        String productId = "test";

        stubFor(get(urlEqualTo("/product/" + productId))
                .willReturn(aResponse().withStatus(404)));

        assertThrows(ProductNotFoundException.class, () -> productClient.getProduct(productId));
    }
}
