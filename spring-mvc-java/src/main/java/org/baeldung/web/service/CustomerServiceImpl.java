package org.baeldung.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.baeldung.model.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private HashMap<String, Customer> customerMap;

    public CustomerServiceImpl() {
        customerMap = new HashMap<String, Customer>();

        customerMap.put("10A", new Customer("10A", "Jane", "ABC Company"));
        customerMap.put("20B", new Customer("20B", "Bob", "XYZ Company"));
    }

    @Override
    public List<Customer> allCustomers() {
        return new ArrayList<Customer>(customerMap.values());
    }

    @Override
    public Customer getCustomerDetail(final String id) {
        return customerMap.get(id);
    }

}
