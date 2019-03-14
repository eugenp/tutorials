package com.baeldung.hexagonal.store.core.context.customer.service;

import com.baeldung.hexagonal.store.core.context.customer.entity.Customer;
import com.baeldung.hexagonal.store.core.context.customer.infrastructure.CustomerDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
    private CustomerDataStore customerDataStore;

    @Autowired
    public CustomerServiceImpl(CustomerDataStore customerDataStore) {
        this.customerDataStore = customerDataStore;
    }

    @Override
    public Iterable<Customer> getAllCustomers() {
        return this.customerDataStore.findAll();
    }
}
