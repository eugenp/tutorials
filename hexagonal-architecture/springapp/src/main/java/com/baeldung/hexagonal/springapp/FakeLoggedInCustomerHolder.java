package com.baeldung.hexagonal.springapp;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.core.Customer;
import com.baeldung.hexagonal.core.LoggedInCustomerHolder;
import com.baeldung.hexagonal.springapp.repository.JpaCustomerRepository;

@Service
public class FakeLoggedInCustomerHolder implements LoggedInCustomerHolder {

    JpaCustomerRepository customerRepository;

    public FakeLoggedInCustomerHolder(JpaCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override public Customer getCustomer() {
        return customerRepository.getCustomer("john.doe@example.org").orElseThrow(() -> new IllegalStateException("not found by email"));
    }

}
