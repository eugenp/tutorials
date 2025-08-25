package com.baeldung.ruleengine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.baeldung.ruleengine.model.Customer;
import com.baeldung.ruleengine.model.Order;

public class SpelRuleUnitTest {

    @Test
    void whenLoyalCustomer_thenEligibleForDiscount() {
        Customer customer = new Customer("Bob", 730, false);
        Order order = new Order(200.0, customer);

        SpelRule rule = new SpelRule(
            "#order.customer.loyaltyPoints > 500",
            "Loyalty discount rule"
        );
        assertTrue(rule.evaluate(order));
    }

    @Test
    void whenFirstOrderHighAmount_thenEligibleForSpecialDiscount() {
        Customer customer = new Customer("Bob", 0, true);
        Order order = new Order(800.0, customer);

        SpelRule approvalRule = new SpelRule(
            "#order.customer.firstOrder and #order.amount > 500",
            "First-time customer with high order gets special discount"
        );
        assertTrue(approvalRule.evaluate(order));
    }
}
