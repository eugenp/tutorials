package com.baeldung.ddd.order.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/*
 To run this test we need to run the databases first.
 A dedicated docker-compose.yml file is located under the resources directory.
 We can run it by simple executing `docker-compose up`.
 */
@SpringJUnitConfig
@SpringBootTest
public class PersistOrderLiveTest {
    @Autowired
    private JpaOrderRepository repository;

    @DisplayName("given order with two line items, when persist, then order is saved")
    @Test
    public void test() throws Exception {
        // given
        JpaOrder order = prepareTestOrderWithTwoLineItems();

        // when
        JpaOrder savedOrder = repository.save(order);

        // then
        JpaOrder foundOrder = repository.findById(savedOrder.getId())
            .get();
        assertThat(foundOrder.getOrderLines()).hasSize(2);
    }

    private JpaOrder prepareTestOrderWithTwoLineItems() {
        JpaOrderLine ol0 = new JpaOrderLine(new JpaProduct(BigDecimal.valueOf(10.00), "USD"), 2);
        JpaOrderLine ol1 = new JpaOrderLine(new JpaProduct(BigDecimal.valueOf(5.00), "USD"), 10);
        return new JpaOrder(Arrays.asList(ol0, ol1));
    }
}
