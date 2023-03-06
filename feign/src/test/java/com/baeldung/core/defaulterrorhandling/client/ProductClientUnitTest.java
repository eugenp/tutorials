package com.baeldung.core.defaulterrorhandling.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.core.ExampleApplication;
import com.baeldung.core.defaulterrorhandling.model.Product;
import com.github.tomakehurst.wiremock.WireMockServer;

import feign.FeignException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExampleApplication.class)
public class ProductClientUnitTest {

    @Autowired
    private ProductClient productClient;

    private WireMockServer wireMockServer;

    @Before
    public void startWireMockServer() {
        wireMockServer = new WireMockServer(8084);
        configureFor("localhost", 8084);
        wireMockServer.start();

    }

    @After
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    public void givenProductIsAvailable_whenGetProductCalled_thenReturnMatchingProduct() {
        String productId = "test";

        String productResponse = "{ " +
                "    \"id\":\"test\",\n" +
                "    \"productName\":\"Watermelon\",\n" +
                "    \"price\":12\n" +
                "}";

        stubFor(get(urlEqualTo("/product/" + productId))
            .willReturn(aResponse()
            .withStatus(HttpStatus.OK.value())
            .withHeader("Content-Type", "application/json")
            .withBody(productResponse)));

        Product product = productClient.getProduct(productId);

        assertEquals(productId, product.getId());
        assertEquals("Watermelon", product.getProductName());
        assertEquals(12.00d, product.getPrice(), 0.00d);
    }

    @Test
    public void givenProductApiIsNotAvailable_whenGetProductCalled_thenThrowFeignException() {
        String productId = "test";

        stubFor(get(urlEqualTo("/product/" + productId))
            .willReturn(aResponse()
            .withStatus(HttpStatus.SERVICE_UNAVAILABLE.value())));

        assertThrows(FeignException.class, () -> productClient.getProduct(productId));
    }

    @Test
    public void givenProductIdNotFound_whenGetProductCalled_thenThrowFeignException() {
        String productId = "test";

        stubFor(get(urlEqualTo("/product/" + productId))
            .willReturn(aResponse()
            .withStatus(HttpStatus.NOT_FOUND.value())));

        assertThrows(FeignException.class, () -> productClient.getProduct(productId));
    }
}
