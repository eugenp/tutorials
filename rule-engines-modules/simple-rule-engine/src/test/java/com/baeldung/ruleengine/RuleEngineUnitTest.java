package com.baeldung.ruleengine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.baeldung.ruleengine.model.Customer;
import com.baeldung.ruleengine.model.Order;

public class RuleEngineUnitTest {

    @Test
    void whenTwoRulesTriggered_thenBothDescriptionsReturned() {
        Customer customer = new Customer("Max", 550, true);
        Order order = new Order(600.0, customer);

        RuleEngine engine = new RuleEngine(List.of(new LoyaltyDiscountRule(), new FirstOrderHighValueSpecialDiscountRule()));

        List<String> results = engine.evaluate(order);

        assertEquals(2, results.size());
        assertTrue(results.contains("Loyalty Discount Rule: Customer has more than 500 points"));
        assertTrue(results.contains("First Order Special Discount Rule: First Time customer with high value order"));
    }

    @Test
    void whenNoRulesTriggered_thenEmptyListReturned() {
        Customer customer = new Customer("Max", 50, false);
        Order order = new Order(200.0, customer);

        RuleEngine engine = new RuleEngine(List.of(new LoyaltyDiscountRule(), new FirstOrderHighValueSpecialDiscountRule()));

        List<String> results = engine.evaluate(order);

        assertTrue(results.isEmpty());
    }
}
