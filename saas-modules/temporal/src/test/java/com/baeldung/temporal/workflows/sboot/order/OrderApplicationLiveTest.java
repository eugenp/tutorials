package com.baeldung.temporal.workflows.sboot.order;

import com.baeldung.temporal.workflows.sboot.order.adapter.rest.OrderApi;
import com.baeldung.temporal.workflows.sboot.order.config.OrderWorkflowConfiguration;
import com.baeldung.temporal.workflows.sboot.order.domain.*;
import org.apache.catalina.core.ApplicationContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderApplicationLiveTest {

    @LocalServerPort
    int port;

    @Autowired
    ApplicationContext context;


    @Test
    public void whenHappyPathOrder_thenWorkflowSucceeds() throws Exception{

        assertNotNull(context);

        RestClient client = RestClient.create("http://localhost:" + port);

        var orderSpec = createTestOrder();

        var orderResponse = client.post()
          .body(orderSpec)
          .retrieve()
          .body(OrderApi.OrderCreationResponse.class);

        assertNotNull(orderResponse);

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

          ),
          new Customer(
            UUID.randomUUID(),
            "John Doe",
            LocalDate.of(1970,1,1)
          )
        );
    }

}