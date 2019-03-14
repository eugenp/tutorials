package com.baeldung.hexagonal.store.persistence.repo.customer;

import com.baeldung.hexagonal.store.core.context.customer.entity.Customer;
import com.baeldung.hexagonal.store.core.context.customer.infrastructure.CustomerDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerRepositoryImpl implements CustomerDataStore {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public Iterable<Customer> findAll() {
        return customerRepository.findAll();
    }
}