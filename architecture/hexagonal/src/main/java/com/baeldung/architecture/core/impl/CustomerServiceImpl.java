package com.baeldung.architecture.core.impl;

import com.baeldung.architecture.core.domain.Customer;
import com.baeldung.architecture.port.CustomerRepository;
import com.baeldung.architecture.port.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository repository;

    @Override
    public List<Customer> getAll() {
        return repository.getAll();
    }

    @Override
    public void create(Customer customer) {
        repository.create(customer);
    }

    @Override
    public Customer getByName(String name) {
        return repository.getByName(name);
    }
}
