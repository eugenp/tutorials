package com.baeldung.innerinterfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommaSeparatedCustomers implements Customer.List {

    private List<Customer> customers = new ArrayList<Customer>();

    @Override
    public void Add(Customer customer) {
        customers.add(customer);
    }

    @Override
    public String getCustomerNames() {
        return customers.stream().map(customer -> customer.getName()).collect(Collectors.joining(","));
    }

}
