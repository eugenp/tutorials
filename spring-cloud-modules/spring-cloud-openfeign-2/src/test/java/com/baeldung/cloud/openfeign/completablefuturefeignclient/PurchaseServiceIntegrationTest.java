package com.baeldung.cloud.openfeign.completablefuturefeignclient;

import com.baeldung.cloud.openfeign.ExampleApplication;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.ExecutionException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;

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
    }

    @AfterEach
    public void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    void executePurchase() throws ExecutionException, InterruptedException {
        stubFor(post(urlEqualTo("/purchase?site_id=BR"))
                .willReturn(aResponse().withStatus(HttpStatus.NOT_FOUND.value())));

        stubFor(post(urlEqualTo("/reports"))
                .willReturn(aResponse().withStatus(HttpStatus.OK.value())));

        Purchase purchase = new Purchase("BR");

        String result = purchaseService.executePurchase(purchase);

        assertNull(result);
    }
}