package com.baeldung.evrete.introduction;

import com.baeldung.evrete.introduction.model.Customer;
import com.baeldung.evrete.introduction.model.Invoice;
import org.evrete.KnowledgeService;
import org.evrete.api.Knowledge;
import org.evrete.api.RuleSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.*;

class IntroductionAJRUnitTest {
    private static KnowledgeService service;

    @BeforeAll
    static void setUpClass() {
        service = new KnowledgeService();
    }

    @AfterAll
    static void shutDownClass() {
        service.shutdown();
    }

    /**
     * This test makes sure that each customer's actual total sales is equal to the amount
     * computed by the rule engine
     */
    @ParameterizedTest
    @ValueSource(strings = {"true", "false"})
    void sessionTotalsTest(String type) throws IOException  {
        boolean stateful = Boolean.parseBoolean(type);
        ClassLoader classLoader = IntroductionAJR.class.getClassLoader();
        KnowledgeService service = new KnowledgeService();
        Knowledge knowledge = service
            .newKnowledge(
                "JAVA-SOURCE",
                classLoader.getResource("rules/SalesRuleset.java")
            );


        List<Customer> customers = Arrays.asList(
            new Customer("Customer A"),
            new Customer("Customer B"),
            new Customer("Customer C")
        );
        Collection<Object> sessionData = new LinkedList<>(customers);

        HashMap<Customer, Double> actualTotals = new HashMap<>();
        Random random = new Random();
        for (int i = 0; i < 1_000; i++) {
            Customer randomCustomer = customers.get(random.nextInt(customers.size()));
            Invoice invoice = new Invoice(randomCustomer, random.nextInt(100));
            sessionData.add(invoice);

            Double d = actualTotals.get(randomCustomer);
            if(d == null) {
                d = 0.0;
            }
            d = d + invoice.getAmount();
            actualTotals.put(randomCustomer, d);
        }

        RuleSession<?> session = stateful ? knowledge.newStatefulSession() : knowledge.newStatelessSession();
        session
            .insert(sessionData)
            .fire();

        for(Customer c : customers) {
            double d1 = c.getTotal();
            double d2 = actualTotals.get(c);
            assert d1 == d2;
        }

    }
}