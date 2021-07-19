package com.baeldung.architecture.core.impl;

import com.baeldung.architecture.core.domain.Customer;
import com.baeldung.architecture.port.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public void create(Customer customer) {

    }

    @Override
    public Customer getByName(String id) {
        return null;
    }
}
