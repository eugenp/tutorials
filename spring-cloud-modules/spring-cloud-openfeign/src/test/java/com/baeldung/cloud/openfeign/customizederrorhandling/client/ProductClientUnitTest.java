package com.baeldung.cloud.openfeign.customizederrorhandling.client;

import com.baeldung.cloud.openfeign.customizederrorhandling.exception.ProductNotFoundException;
import com.baeldung.cloud.openfeign.customizederrorhandling.exception.ProductServiceNotAvailableException;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductClientUnitTest {

    @Autowired
    private ProductClient productClient;

    private WireMockServer wireMockServer;

    @Before
    public void startWireMockServer() {
        wireMockServer = new WireMockServer(8081);
        configureFor("localhost", 8081);
        wireMockServer.start();
    }


    @Test
    public void givenProductApiIsNotAvailable_whenGetProductCalled_thenThrowProductServiceNotAvailableException() {
        String productId = "test";

        stubFor(get(urlEqualTo("/product/" + productId))
                .willReturn(aResponse().withStatus(503)));

        Assert.assertThrows(ProductServiceNotAvailableException.class, () -> productClient.getProduct(productId));
    }

    @Test
    public void givenProductNotFound_whenGetProductCalled_thenThrowBadRequestException() {
        String productId = "test";

        stubFor(get(urlEqualTo("/product/" + productId))
                .willReturn(aResponse().withStatus(404)));

        Assert.assertThrows(ProductNotFoundException.class, () -> productClient.getProduct(productId));
    }

    @After
    public void stopWireMockServer() {
        wireMockServer.stop();
    }
}
