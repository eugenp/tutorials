package com.baeldung.dependencyinjection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceFieldBased implements CustomerService {

    @Autowired
    private CustomerRepository repository;
    
    @Override
    public List<Customer> getAll() {
        return repository.findAll();
    }

}
