package com.baeldung.evrete.introduction;

import org.evrete.KnowledgeService;
import org.evrete.api.Knowledge;
import com.baeldung.evrete.introduction.model.*;

import java.util.*;

public class IntroductionInline {
    public static void main(String[] args) {
        KnowledgeService service = new KnowledgeService();
        Knowledge knowledge = service
            .newKnowledge()
            .newRule("Clear total sales")
            .forEach("$c", Customer.class)
            .execute(ctx -> {
                Customer c = ctx.get("$c");
                c.setTotal(0.0);
            })
            .newRule("Compute totals")
            .forEach(
                    "$c", Customer.class,
                    "$i", Invoice.class
            )
            .where("$i.customer == $c")
            .execute(ctx -> {
                Customer c = ctx.get("$c");
                Invoice i = ctx.get("$i");
                c.addToTotal(i.getAmount());
            });


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
