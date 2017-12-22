package com.baeldung.service;

import org.springframework.stereotype.Component;

import com.baeldung.builder.CustomerBuilder;
import com.baeldung.model.Customer;

@Component
public class CustomerService {

    public Customer getCustomer(int customerId) {
        CustomerBuilder customerBuilder = new CustomerBuilder();
        return customerBuilder.getCustomer(customerId);
    }

}
