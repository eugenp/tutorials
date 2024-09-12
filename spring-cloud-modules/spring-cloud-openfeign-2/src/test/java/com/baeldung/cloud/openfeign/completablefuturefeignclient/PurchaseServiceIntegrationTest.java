package com.baeldung.cloud.openfeign.completablefuturefeignclient;

import com.baeldung.cloud.openfeign.ExampleApplication;
import com.github.tomakehurst.wiremock.WireMockServer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ExampleApplication.class)
class PurchaseServiceIntegrationTest {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private PaymentMethodClient paymentMethodClient;

    @Autowired
    private ReportClient reportClient;

    private WireMockServer wireMockServer;

    @BeforeEach
    public void startWireMockServer() {
        wireMockServer = new WireMockServer(8083);
        configureFor("localhost", 8083);
        wireMockServer.start();

        stubFor(post(urlEqualTo("/reports")).willReturn(aResponse().withStatus(HttpStatus.OK.value())));
    }

    @AfterEach
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    void givenRestCalls_whenBothReturnsOk_thenReturnCorrectResult() throws ExecutionException, InterruptedException {
        stubFor(get(urlEqualTo("/payment_methods?site_id=BR")).willReturn(aResponse().withStatus(HttpStatus.OK.value())
            .withBody("credit_card")));

        String result = purchaseService.executePurchase("BR");

        assertNotNull(result);
        assertEquals("Purchase executed with payment method credit_card", result);
    }

    @Test
    void givenRestCalls_whenPurchaseReturns404_thenReturnDefault() throws ExecutionException, InterruptedException {
        stubFor(get(urlEqualTo("/payment_methods?site_id=BR")).willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND.value())));

        String result = purchaseService.executePurchase("BR");

        assertNotNull(result);
        assertEquals("Purchase executed with payment method cash", result);
    }

    @Test
    @Disabled
    void givenRestCalls_whenPurchaseCompletableFutureTimeout_thenThrowNewException() {
        stubFor(get(urlEqualTo("/payment_methods?site_id=BR")).willReturn(aResponse().withFixedDelay(550)));

        Throwable error = assertThrows(ExecutionException.class, () -> purchaseService.executePurchase("BR"));

        assertEquals("java.lang.RuntimeException: Thread timeout!", error.getMessage());
    }

    @Test
    void givenRestCalls_whenPurchaseRequestWebTimeout_thenThrowNewException() {
        stubFor(get(urlEqualTo("/payment_methods?site_id=BR")).willReturn(aResponse().withFixedDelay(250)));

        Throwable error = assertThrows(ExecutionException.class, () -> purchaseService.executePurchase("BR"));

        assertEquals("java.lang.RuntimeException: REST call network timeout!", error.getMessage());
    }
}