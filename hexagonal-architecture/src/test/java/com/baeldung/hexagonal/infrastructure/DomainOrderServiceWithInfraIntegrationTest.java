package com.baeldung.hexagonal.infrastructure;

import com.baeldung.hexagonal.domain.Order;
import com.baeldung.hexagonal.domain.service.DomainOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.data.Percentage.withPercentage;

@SpringBootTest
class DomainOrderServiceWithInfraIntegrationTest {

    @Autowired
    private DomainOrderService domainOrderService;

    @Test
    void givenExistingOrder_whenFindingOrder_thenReturnCorrectOrder() {
        Order initialOrder = new Order();
        initialOrder.setPrice(new BigDecimal(1000));
        domainOrderService.createOrder(initialOrder);

        Order retrievedOrder = this.domainOrderService.findById(initialOrder.getId());
        assertThat(initialOrder.getPrice()).isCloseTo(retrievedOrder.getPrice(), withPercentage(0.0001));
    }
}