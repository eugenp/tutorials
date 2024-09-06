package com.baeldung.cloud.openfeign.completablefuturefeignclient;

import com.baeldung.cloud.openfeign.ExampleApplication;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ExampleApplication.class)
@TestPropertySource(locations = "classpath:application-integration_test.properties")
class PurchaseServiceIntegrationTest {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PaymentMethodClient paymentMethodClient;

    @Autowired
    private ReportClient reportClient;

    private WireMockServer wireMockServer;

    private Purchase purchase;

    @BeforeEach
    public void startWireMockServer() {
        wireMockServer = new WireMockServer(8083);
        configureFor("localhost", 8083);
        wireMockServer.start();

        stubFor(post(urlEqualTo("/reports"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())));

        purchase = new Purchase("BR");
    }

    @AfterEach
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    void givenRestCalls_whenBothReturnsOk_thenReturnCorrectResult()
            throws ExecutionException, InterruptedException {
        stubFor(post(urlEqualTo("/purchase?site_id=BR"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value()).withBody("credit_card")));

        String result = purchaseService.executePurchase(purchase);

        assertNotNull(result);
        assertEquals("Purchase executed with payment method credit_card", result);
    }

    @Test
    void givenRestCalls_whenPurchaseReturns404_thenReturnDefault()
            throws ExecutionException, InterruptedException {
        stubFor(post(urlEqualTo("/purchase?site_id=BR"))
                .willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND.value())));

        String result = purchaseService.executePurchase(purchase);

        assertNotNull(result);
        assertEquals("Purchase executed with payment method account_money", result);
    }

    @Test
    void givenRestCalls_whenPurchaseCompletableFutureTimeout_thenReturnDefault() {
        stubFor(post(urlEqualTo("/purchase?site_id=BR"))
                .willReturn(aResponse().withFixedDelay(450)));

        Throwable error = assertThrows(ExecutionException.class, () -> purchaseService.executePurchase(purchase));

        assertEquals("java.lang.RuntimeException: Thread timeout!", error.getMessage());
    }

//    @Test
//    void givenRestCalls_whenPurchaseRequestWebTimeout_thenReturnDefault() {
//        stubFor(post(urlEqualTo("/purchase?site_id=BR"))
//                .willReturn(aResponse().withFixedDelay(250)));
//
//        Throwable error = assertThrows(ExecutionException.class, () -> purchaseService.executePurchase(purchase));
//
//        assertEquals("REST call network timeout!", error.getMessage());
//    }
}