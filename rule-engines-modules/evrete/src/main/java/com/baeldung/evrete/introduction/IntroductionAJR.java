package com.baeldung.evrete.introduction;

import com.baeldung.evrete.introduction.model.Customer;
import com.baeldung.evrete.introduction.model.Invoice;
import org.evrete.KnowledgeService;
import org.evrete.api.Knowledge;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class IntroductionAJR {
    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = IntroductionAJR.class.getClassLoader();
        KnowledgeService service = new KnowledgeService();
        URL rulesetUrl = classLoader.getResource("rules/SalesRuleset.java");
        Knowledge knowledge = service.newKnowledge(
            "JAVA-SOURCE",
            rulesetUrl
        );

        List<Customer> customers = Arrays.asList(
            new Customer("Customer A"),
            new Customer("Customer B"),
            new Customer("Customer C")
        );

        Random random = new Random();
        Collection<Object> sessionData = new LinkedList<>(customers);
        for (int i = 0; i < 100_000; i++) {
            Customer randomCustomer = customers.get(random.nextInt(customers.size()));
            Invoice invoice = new Invoice(randomCustomer, 100 * random.nextDouble());
            sessionData.add(invoice);
        }

        knowledge
            .newStatelessSession()
            .insert(sessionData)
            .fire();

        for (Customer c : customers) {
            System.out.printf("%s:\t$%,.2f%n", c.getName(), c.getTotal());
        }

        service.shutdown();
    }
}
