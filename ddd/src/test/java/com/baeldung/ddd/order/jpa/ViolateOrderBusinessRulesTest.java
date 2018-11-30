package com.baeldung.ddd.order.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ViolateOrderBusinessRulesTest {
    @DisplayName("given two non-zero order line items, when create an order with them, it's possible to set total cost to zero")
    @Test
    void test() throws Exception {
        // given
        // available products
        JpaProduct lungChingTea = new JpaProduct(BigDecimal.valueOf(10.00), "USD");
        JpaProduct gyokuroMiyazakiTea = new JpaProduct(BigDecimal.valueOf(20.00), "USD");
        // Lung Ching tea order line
        JpaOrderLine orderLine0 = new JpaOrderLine(lungChingTea, 2);
        // Gyokuro Miyazaki tea order line
        JpaOrderLine orderLine1 = new JpaOrderLine(gyokuroMiyazakiTea, 3);

        // when
        // create the order
        JpaOrder order = new JpaOrder();
        order.addLineItem(orderLine0);
        order.addLineItem(orderLine1);
        order.setTotalCost(BigDecimal.ZERO);
        order.setCurrencyUnit("USD");

        // then
        // this doesn't look good...
        assertThat(order.getTotalCost()).isEqualTo(BigDecimal.ZERO);
    }
}
