package com.baeldung.hexagonal.order.rest;

import com.baeldung.hexagonal.HexagonalApplication;
import com.baeldung.hexagonal.order.Order;
import com.baeldung.hexagonal.order.OrderLine;
import com.baeldung.hexagonal.order.Product;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.logging.Logger;

@SpringJUnitConfig()
@SpringBootTest(classes = {HexagonalApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OrderRestIntegrationTest {
    private final Logger logger = Logger.getLogger(OrderRestIntegrationTest.class.getName());

    @DisplayName("Testing creation")
    @Test
    void test() {
        Order order = prepareTestOrderWithTwoLineItems();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Order> entity = new HttpEntity<>(order);

        ResponseEntity<Void> exchange = restTemplate.exchange("http://localhost:8080/api/order", HttpMethod.POST, entity, Void.class);
        logger.info("Status: " + exchange.getStatusCode());
        assert(exchange.getStatusCode() == HttpStatus.OK);
    }

    private Order prepareTestOrderWithTwoLineItems() {
        OrderLine ol0 = new OrderLine(new Product(Money.of(CurrencyUnit.USD, 10.00)), 2);
        OrderLine ol1 = new OrderLine(new Product(Money.of(CurrencyUnit.USD, 5.00)), 10);
        return new Order(Arrays.asList(ol0, ol1));
    }

}
