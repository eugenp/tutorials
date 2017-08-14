package com.baeldung.dependencyinjection;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class InMemCustomerRepository implements CustomerRepository {

    @Override
    public Customer findById(Long customerId) {
        List<Customer> customers = Arrays.asList(
          new Customer(1L, "customer1"),
          new Customer(2L, "customer2"),
          new Customer(3L, "customer3"));
        
        return customers.stream()
          .filter(customer -> customer.getId().equals(customerId))
          .findFirst()
          .orElseThrow(CustomerNotFoundException::new);
    }
}
