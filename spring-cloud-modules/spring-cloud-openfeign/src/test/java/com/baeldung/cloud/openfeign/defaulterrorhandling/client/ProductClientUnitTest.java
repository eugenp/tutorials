package com.baeldung.cloud.openfeign.defaulterrorhandling.client;

import com.baeldung.cloud.openfeign.defaulterrorhandling.model.Product;
import com.github.tomakehurst.wiremock.WireMockServer;
import feign.FeignException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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

        Assert.assertEquals(productId, product.getId());
        Assert.assertEquals("Watermelon", product.getProductName());
        Assert.assertEquals(12.00d, product.getPrice(), 0.00d);
    }

    @Test
    public void givenProductApiIsNotAvailable_whenGetProductCalled_thenThrowFeignException() {
        String productId = "test";

        stubFor(get(urlEqualTo("/product/" + productId))
                .willReturn(aResponse().withStatus(HttpStatus.SERVICE_UNAVAILABLE.value())));

        Assert.assertThrows(FeignException.class, () -> productClient.getProduct(productId));
    }

    @Test
    public void givenProductIdNotFound_whenGetProductCalled_thenThrowFeignException() {
        String productId = "test";

        stubFor(get(urlEqualTo("/product/" + productId))
                .willReturn(aResponse()
                .withStatus(HttpStatus.NOT_FOUND.value())));

        Assert.assertThrows(FeignException.class, () -> productClient.getProduct(productId));
    }

    @After
    public void stopWireMockServer() {
        wireMockServer.stop();
    }
}
