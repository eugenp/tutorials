package com.baeldung.ddd.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.ArrayList;
import java.util.Arrays;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderUnitTest {
    @DisplayName("given order with two items, when calculate total cost, then sum is returned")
    @Test
    void test0() throws Exception {
        // given
        OrderLine ol0 = new OrderLine(new Product(Money.of(CurrencyUnit.USD, 10.00)), 2);
        OrderLine ol1 = new OrderLine(new Product(Money.of(CurrencyUnit.USD, 5.00)), 10);
        Order order = new Order(Arrays.asList(ol0, ol1));

        // when
        Money totalCost = order.totalCost();

        // then
        assertThat(totalCost).isEqualTo(Money.of(CurrencyUnit.USD, 70.00));
    }

    @DisplayName("when create order without line items, then exception is thrown")
    @Test
    void test1() throws Exception {
        // when
        Throwable throwable = catchThrowable(() -> new Order(new ArrayList<>()));

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("given order with two line items, when add another line item, then total cost is updated")
    @Test
    void test2() throws Exception {
        // given
        OrderLine ol0 = new OrderLine(new Product(Money.of(CurrencyUnit.USD, 10.00)), 1);
        OrderLine ol1 = new OrderLine(new Product(Money.of(CurrencyUnit.USD, 5.00)), 1);
        Order order = new Order(Arrays.asList(ol0, ol1));

        // when
        order.addLineItem(new OrderLine(new Product(Money.of(CurrencyUnit.USD, 20.00)), 2));

        // then
        assertThat(order.totalCost()).isEqualTo(Money.of(CurrencyUnit.USD, 55));
    }

    @DisplayName("given order with three line items, when remove item, then total cost is updated")
    @Test
    void test3() throws Exception {
        // given
        OrderLine ol0 = new OrderLine(new Product(Money.of(CurrencyUnit.USD, 10.00)), 1);
        OrderLine ol1 = new OrderLine(new Product(Money.of(CurrencyUnit.USD, 20.00)), 1);
        OrderLine ol2 = new OrderLine(new Product(Money.of(CurrencyUnit.USD, 30.00)), 1);
        Order order = new Order(Arrays.asList(ol0, ol1, ol2));

        // when
        order.removeLineItem(1);

        // then
        assertThat(order.totalCost()).isEqualTo(Money.of(CurrencyUnit.USD, 40.00));
    }
}
