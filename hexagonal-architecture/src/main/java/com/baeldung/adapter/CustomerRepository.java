package com.baeldung.adapter;

import com.baeldung.domain.entity.Customer;

public class CustomerRepository {
    Customer findById() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Carlos");

        return customer;
    }
}
