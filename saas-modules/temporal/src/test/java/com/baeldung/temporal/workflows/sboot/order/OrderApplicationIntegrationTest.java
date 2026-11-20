package com.baeldung.temporal.workflows.sboot.order;

import com.baeldung.temporal.workflows.sboot.order.adapter.rest.OrderApi;
import com.baeldung.temporal.workflows.sboot.order.domain.*;
import com.baeldung.temporal.workflows.sboot.order.services.InventoryService;
import io.temporal.spring.boot.TemporalOptionsCustomizer;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.WorkerFactoryOptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
  properties = {
    "spring.temporal.test-server.enabled=true"
  },
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderApplicationIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    TestWorkflowEnvironment testEnv;

    @Autowired
    InventoryService inventoryService;

    @Test
    @Timeout(15)
    public void whenHappyPathOrder_thenWorkflowSucceeds() {

        RestClient client = RestClient.create("http://localhost:" + port);

        var orderSpec = createTestOrder("hp");
        var orderResponse = client.post()
          .uri("/order")
          .body(orderSpec)
          .retrieve()
          .toEntity(OrderApi.OrderCreationResponse.class);

        assertNotNull(orderResponse);
        assertThat(orderResponse)
          .isNotNull()
          .satisfies(e -> e.getStatusCode().is2xxSuccessful());

        var orderExecutionId = orderResponse.getBody().orderExecutionId();

        // Query payment so we can get the transaction id and simulate an authorization.
        // Since query methods can't block, we may get a 204 response, which
        // means the workflow hasn't reached the step where a payment request is generated.
        // For testing purposes, we'll simply poll the server until we get a response.
        ResponseEntity<PaymentAuthorization> payment = null;
        do {
            payment = client.get()
              .uri("/order/{orderExecutionId}/payment", orderExecutionId)
              .retrieve()
              .toEntity(PaymentAuthorization.class);

            assertTrue(payment.getStatusCode().is2xxSuccessful());
            if ( payment.getStatusCode().value() == 200) {
                break;
            }
            else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        while( true );

        assertThat(payment.getBody())
          .isNotNull()
          .satisfies(p -> assertThat(p.transactionId()).isNotNull());

        // Signal payment accepted
        var r1 = client.put()
          .uri("/order/{orderExecutionId}/paymentStatus", orderExecutionId)
          .body(new OrderApi.PaymentStatusUpdateInfo(
            PaymentStatus.APPROVED,
            "auth124",
            payment.getBody().transactionId(),
            null
            )
          )
          .retrieve()
          .toEntity(Void.class);

        assertThat(r1)
          .isNotNull()
          .satisfies(e -> e.getStatusCode().is2xxSuccessful());

        // Signal package dispatched for delivery
        var r2 = client.put()
          .uri("/order/{orderExecutionId}/shippingStatus", orderExecutionId)
          .body(new OrderApi.ShippingStatusUpdateInfo(
            ShippingStatus.SHIPPED, Instant.now()))
          .retrieve()
          .toEntity(Void.class);

        assertThat(r2)
          .isNotNull()
          .satisfies(e -> e.getStatusCode().is2xxSuccessful());

        // Signal package delivered at final destination
        var r3 = client.put()
          .uri("/order/{orderExecutionId}/shippingStatus", orderExecutionId)
          .body(new OrderApi.ShippingStatusUpdateInfo(
            ShippingStatus.DELIVERED, Instant.now()))
          .retrieve()
          .toEntity(Void.class);

        assertThat(r3)
          .isNotNull()
          .satisfies(e -> e.getStatusCode().is2xxSuccessful());

        // Get shipping state
        var r4 = client.get()
          .uri("/order/{orderExecutionId}/shipping", orderExecutionId)
          .retrieve()
          .toEntity(Shipping.class);
        assertThat(r4)
          .isNotNull()
          .satisfies(e -> e.getStatusCode().is2xxSuccessful());
        var shipping = r4.getBody();
        assertThat(shipping)
          .satisfies(s -> assertThat(s.status()).isEqualTo(ShippingStatus.DELIVERED))
          .satisfies(s -> assertThat(s.history().size()).isEqualTo(4));

    }

    @Test
    @Timeout(15)
    public void whenPickupTimeout_thenItemsReturnToStock() {

        RestClient client = RestClient.create("http://localhost:" + port);

        var orderSpec = createTestOrder("pt");
        var orderResponse = client.post()
          .uri("/order")
          .body(orderSpec)
          .retrieve()
          .toEntity(OrderApi.OrderCreationResponse.class);

        assertNotNull(orderResponse);
        assertThat(orderResponse)
          .isNotNull()
          .satisfies(e -> e.getStatusCode().is2xxSuccessful());

        var orderExecutionId = orderResponse.getBody().orderExecutionId();

        // Query payment so we can get the transaction id and simulate an authorization.
        // Since query methods can't block, we may get a 204 response, which
        // means the workflow hasn't reached the step where a payment request is generated.
        // For testing purposes, we'll simply poll the server until we get a response.
        ResponseEntity<PaymentAuthorization> payment = null;
        do {
            payment = client.get()
              .uri("/order/{orderExecutionId}/payment", orderExecutionId)
              .retrieve()
              .toEntity(PaymentAuthorization.class);

            assertTrue(payment.getStatusCode().is2xxSuccessful());
            if ( payment.getStatusCode().value() == 200) {
                break;
            }
            else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        while( true );

        assertThat(payment.getBody())
          .isNotNull()
          .satisfies(p -> assertThat(p.transactionId()).isNotNull());

        // Signal payment accepted
        var r1 = client.put()
          .uri("/order/{orderExecutionId}/paymentStatus", orderExecutionId)
          .body(new OrderApi.PaymentStatusUpdateInfo(
              PaymentStatus.APPROVED,
              "auth124",
              payment.getBody().transactionId(),
              null
            )
          )
          .retrieve()
          .toEntity(Void.class);

        assertThat(r1)
          .isNotNull()
          .satisfies(e -> e.getStatusCode().is2xxSuccessful());

        // Fast-forward 1 day to force a the delivery timeout
        testEnv.sleep(Duration.ofDays(1));

        // Wait until the workflow completes
        testEnv.getWorkflowClient().newUntypedWorkflowStub(orderExecutionId).getResult(Void.class);

        // Get shipping state
        var r4 = client.get()
          .uri("/order/{orderExecutionId}/shipping", orderExecutionId)
          .retrieve()
          .toEntity(Shipping.class);
        assertThat(r4)
          .isNotNull()
          .satisfies(e -> e.getStatusCode().is2xxSuccessful());
        var shipping = r4.getBody();
        assertThat(shipping)
          .satisfies(s -> assertThat(s.status()).isEqualTo(ShippingStatus.CANCELLED));

        // Check inventory for order items
        orderSpec.order().items().forEach(item -> {
            var actual = inventoryService.getInventory(item.sku());
            assertThat(actual.quantity()).isEqualTo(0);
        });
    }

    // Create a test order
    private static OrderSpec createTestOrder(String skuPrefix) {

        return new OrderSpec(
           new Order(
             UUID.randomUUID(),
             List.of(new OrderItem(skuPrefix+"-sku1", 10), new OrderItem(skuPrefix+"-sku2", 20))
           ),
          new BillingInfo(
            "XXXX1234AAAABBBBZZZZ",
            new BigDecimal("500.00"),
            "USD"
          ),
          new ShippingInfo(
            "Mr. Beagle Doggo",
            "345, St. Louis Ave.",
            "",
            "123456",
            "Cannes",
            "SA",
            "TT",
            "+292 1 555 1234",
            "doggo@example.com",
            null,
            "Throw over the gate"
          ),
          new Customer(
            UUID.randomUUID(),
            "John Doe",
            LocalDate.of(1970,1,1)
          )
        );
    }

}