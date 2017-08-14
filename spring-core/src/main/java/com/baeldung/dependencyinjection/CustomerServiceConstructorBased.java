package com.baeldung.dependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceConstructorBased implements CustomerService {

    private final CustomerRepository repository;

    @Autowired
    public CustomerServiceConstructorBased(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer getCustomer(Long customerId) {
        return repository.findById(customerId);
    }
}
