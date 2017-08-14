package com.baeldung.dependencyinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceSetterBased implements CustomerService {

    private CustomerRepository repository;
    
    @Override
    public Customer getCustomer(Long customerId) {
        return repository.findById(customerId);
    }

    @Autowired
    public void setRepository(CustomerRepository repository) {
        this.repository = repository;
    }
}
