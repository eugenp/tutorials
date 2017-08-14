package com.baeldung.dependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceFieldBased implements CustomerService {

    @Autowired
    private CustomerRepository repository;
    
    @Override
    public Customer getCustomer(Long customerId) {
        return repository.findById(customerId);
    }
}
