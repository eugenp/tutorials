package com.baeldung.temporal.workflows.sboot.order;

import com.baeldung.temporal.workflows.sboot.order.adapter.rest.OrderApi;
import com.baeldung.temporal.workflows.sboot.order.domain.*;
import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderApplicationLiveTest {

    @LocalServerPort
    int port;


    @Test
    public void whenHappyPathOrder_thenWorkflowSucceeds() {

        RestClient client = RestClient.create("http://localhost:" + port);

        var orderSpec = createTestOrder();
        var orderResponse = client.post()
          .uri("/order")
          .body(orderSpec)
          .retrieve()
          .toEntity(OrderApi.OrderCreationResponse.class);

        assertNotNull(orderResponse);
        assertThat(orderResponse)
          .isNotNull()
          .satisfies(e -> e.getStatusCode().is2xxSuccessful());

        var orderId = orderResponse.getBody().orderId();

        // Signal payment accepted
        var r1 = client.put()
          .uri("/order/{orderId}/paymentStatus", orderId)
          .body(new OrderApi.PaymentStatusUpdateInfo(
            PaymentStatus.APPROVED,
            "auth1234",
            "tx1234",
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
          .uri("/order/{orderId}/shippingStatus", orderId)
          .body(new OrderApi.ShippingStatusUpdateInfo(
            ShippingStatus.SHIPPED, Instant.now()))
          .retrieve()
          .toEntity(Void.class);

        assertThat(r2)
          .isNotNull()
          .satisfies(e -> e.getStatusCode().is2xxSuccessful());

        // Signal package dispatched for delivery
        var r3 = client.put()
          .uri("/order/{orderId}/shippingStatus", orderId)
          .body(new OrderApi.ShippingStatusUpdateInfo(
            ShippingStatus.DELIVERED, Instant.now()))
          .retrieve()
          .toEntity(Void.class);

        assertThat(r3)
          .isNotNull()
          .satisfies(e -> e.getStatusCode().is2xxSuccessful());

        // Get shipping and payment state

    }

    // Create a test order
    private static OrderSpec createTestOrder() {

        return new OrderSpec(
           new Order(
             UUID.randomUUID(),
             List.of(new OrderItem("sku1", 10), new OrderItem("sku2", 20))
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