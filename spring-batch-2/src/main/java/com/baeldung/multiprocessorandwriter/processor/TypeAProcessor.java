package com.baeldung.multiprocessorandwriter.processor;

import org.springframework.batch.item.ItemProcessor;

import com.baeldung.multiprocessorandwriter.model.Customer;

public class TypeAProcessor implements ItemProcessor<Customer, Customer> {
    @Override
    public Customer process(Customer customer) {
        customer.setName(customer.getName().toUpperCase());
        customer.setEmail("A_" + customer.getEmail());
        return customer;
    }
}