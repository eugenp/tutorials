package com.baeldung.ddd.order.doubledispatch;

import static org.assertj.core.api.Assertions.assertThat;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.baeldung.ddd.order.OrderFixtureUtils;

public class DoubleDispatchDiscountPolicyUnitTest {
    // @formatter:off
    @DisplayName(
        "given regular order with items worth $100 total, " +
        "when apply 10% discount policy, " +
        "then cost after discount is $90"
        )
    // @formatter:on
    @Test
    void test() throws Exception {
        // given
        Order order = new Order(OrderFixtureUtils.orderLineItemsWorthNDollars(100));
        SpecialDiscountPolicy discountPolicy = new SpecialDiscountPolicy() {

            @Override
            public double discount(Order order) {
                return 0.10;
            }

            @Override
            public double discount(SpecialOrder order) {
                return 0;
            }
        };

        // when
        Money totalCostAfterDiscount = order.totalCost(discountPolicy);

        // then
        assertThat(totalCostAfterDiscount).isEqualTo(Money.of(CurrencyUnit.USD, 90));
    }

    // @formatter:off
    @DisplayName(
        "given special order eligible for extra discount with items worth $100 total, " +
        "when apply 20% discount policy for extra discount orders, " +
        "then cost after discount is $80"
        )
    // @formatter:on
    @Test
    void test1() throws Exception {
        // given
        boolean eligibleForExtraDiscount = true;
        Order order = new SpecialOrder(OrderFixtureUtils.orderLineItemsWorthNDollars(100), eligibleForExtraDiscount);
        SpecialDiscountPolicy discountPolicy = new SpecialDiscountPolicy() {

            @Override
            public double discount(Order order) {
                return 0;
            }

            @Override
            public double discount(SpecialOrder order) {
                if (order.isEligibleForExtraDiscount())
                    return 0.20;
                return 0.10;
            }
        };

        // when
        Money totalCostAfterDiscount = order.totalCost(discountPolicy);

        // then
        assertThat(totalCostAfterDiscount).isEqualTo(Money.of(CurrencyUnit.USD, 80.00));
    }
}
