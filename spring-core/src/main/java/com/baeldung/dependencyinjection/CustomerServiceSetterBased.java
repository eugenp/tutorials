package com.baeldung.dependencyinjection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceSetterBased implements CustomerService {

    private CustomerRepository repository;
    
    @Override
    public List<Customer> getAll() {
        return repository.findAll();
    }

    @Autowired
    public void setRepository(CustomerRepository repository) {
        this.repository = repository;
    }
}
