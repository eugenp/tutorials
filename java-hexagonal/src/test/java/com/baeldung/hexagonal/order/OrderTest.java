package com.baeldung.hexagonal.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.Arrays;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {
    @DisplayName("given order with two items, when calculate total cost, then sum is returned")
    @Test
    void test0() throws Exception {
        // given
        OrderLine ol0 = new OrderLine(new Product(Money.of(CurrencyUnit.USD, 10)), 1);
        OrderLine ol1 = new OrderLine(new Product(Money.of(CurrencyUnit.USD, 20)), 1);
        Order order = new Order(Arrays.asList(ol0, ol1));

        // when
        Money totalCost = order.getTotalCost();

        // then
        assertThat(totalCost).isEqualTo(Money.of(CurrencyUnit.USD, 30.00));
    }

}
