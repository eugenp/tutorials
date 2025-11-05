package com.baeldung.synchronous.gateway;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.synchronous.SynchronousRequestsApp;
import com.baeldung.synchronous.system.billing.BillingController;
import com.baeldung.synchronous.system.customer.CustomerController;

@DirtiesContext(classMode = BEFORE_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SynchronousRequestsApp.class)
class ApiGatewayControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private CustomerInfoService customerInfoService;

    @BeforeEach
    void setup() {
        customerInfoService.initializeWebClient(port);
    }

    @Test
    void givenApiGatewayClient_whenBlockingCall_thenResponseReceivedWithinDefinedTimeout() {
        Long customerId = 10L;

        assertTimeout(Duration.ofSeconds(CustomerController.SLEEP_DURATION.getSeconds() + BillingController.SLEEP_DURATION.getSeconds()), () -> {
            testClient.get()
                .uri(uriBuilder -> uriBuilder.path(ApiGatewayController.PATH_CUSTOMER_INFO)
                    .pathSegment(String.valueOf(customerId))
                    .build())
                .exchange()
                .expectStatus()
                .isOk();
        });
    }
}
