package com.baeldung.synchronous.system.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerServiceFake implements CustomerService {

    @Override
    public Customer getBy(Long customerId) {
        return new Customer(customerId, "Baeldung");
    }
}
