package org.baeldung.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.baeldung.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMockServiceImpl {

    private HashMap<String, Customer> customerMap;

    public CustomerMockServiceImpl() {
        customerMap = new HashMap<String, Customer>();

        customerMap.put("10A", new Customer("10A", "Jane", "ABC Company"));
        customerMap.put("20B", new Customer("20B", "Bob", "XYZ Company"));
    }

    public Customer createCustomer(final Customer customer) {
        customerMap.put(customer.getCustomerId(), customer);
        return customer;
    }

    public List<Customer> allCustomers() {
        return new ArrayList<Customer>(customerMap.values());
    }

    public Customer getCustomerDetail(final String id) {
        return customerMap.get(id);
    }

}
