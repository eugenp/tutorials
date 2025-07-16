package com.baeldung.multiprocessorandwriter.processor;

import org.springframework.batch.item.ItemProcessor;

import com.baeldung.multiprocessorandwriter.model.Customer;

public class TypeBProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer customer) {
        customer.setName(customer.getName().toLowerCase());
        customer.setEmail("B_" + customer.getEmail());
        return customer;
    }
}