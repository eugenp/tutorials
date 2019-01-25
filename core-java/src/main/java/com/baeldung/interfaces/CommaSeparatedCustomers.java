package com.baeldung.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * @author zn.wang
 */
public class CommaSeparatedCustomers implements Customer.List {

    private List<Customer> customers = new ArrayList<Customer>();

    @Override
    public void Add(Customer customer) {
        customers.add(customer);
    }

    @Override
    public String getCustomerNames() {
        StringJoiner joiner = new StringJoiner(",");
        for (Customer customer : customers) {
            String name = customer.getName();
            joiner.add(name);
        }
        return joiner.toString();
    }

}
