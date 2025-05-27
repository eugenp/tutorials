package com.baeldung.spring.modulith.application.events.rewards;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class LoyalCustomersRepository {

    private List<LoyalCustomer> customers = new ArrayList<>();

    public Optional<LoyalCustomer> find(String customerId) {
        return customers.stream()
            .filter(it -> it.customerId()
                .equals(customerId))
            .findFirst();
    }

    public void awardPoints(String customerId, int points) {
        var customer = find(customerId).orElseGet(() -> save(new LoyalCustomer(customerId, 0)));

        customers.remove(customer);
        customers.add(customer.addPoints(points));
    }

    public LoyalCustomer save(LoyalCustomer customer) {
        customers.add(customer);
        return customer;
    }

    public boolean isLoyalCustomer(String customerId) {
        return find(customerId).isPresent();
    }

    public record LoyalCustomer(String customerId, int points) {

        LoyalCustomer addPoints(int points) {
            return new LoyalCustomer(customerId, this.points() + points);
        }
    }

}
