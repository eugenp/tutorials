package com.baeldung.beaninjection.service;

import org.springframework.stereotype.Component;

import com.baeldung.beaninjection.builder.CustomerBuilder;
import com.baeldung.beaninjection.model.Customer;

@Component
public class CustomerService {

    public Customer getCustomer(int customerId) {
        CustomerBuilder customerBuilder = new CustomerBuilder();
        return customerBuilder.getCustomer(customerId);
    }

}
