package com.baeldung.ddd.order.doubledispatch;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.baeldung.ddd.order.OrderFixtureUtils;

public class SingleDispatchDiscountPolicyUnitTest {
    // @formatter:off
    @DisplayName(
        "given two discount policies, " +
        "when use these policies, " +
        "then single dispatch chooses the implementation based on runtime type"
        )
    // @formatter:on    
    @Test
    void test() throws Exception {
        // given
        DiscountPolicy flatPolicy = new FlatDiscountPolicy();
        DiscountPolicy amountPolicy = new AmountBasedDiscountPolicy();
        Order orderWorth501Dollars = orderWorthNDollars(501);

        // when
        double flatDiscount = flatPolicy.discount(orderWorth501Dollars);
        double amountDiscount = amountPolicy.discount(orderWorth501Dollars);

        // then
        assertThat(flatDiscount).isEqualTo(0.01);
        assertThat(amountDiscount).isEqualTo(0.1);
    }

    private Order orderWorthNDollars(int totalCost) {
        return new Order(OrderFixtureUtils.orderLineItemsWorthNDollars(totalCost));
    }
}
