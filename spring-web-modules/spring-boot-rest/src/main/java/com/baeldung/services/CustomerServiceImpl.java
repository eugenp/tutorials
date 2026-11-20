package com.baeldung.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.persistence.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

    private HashMap<String, Customer> customerMap;

    public CustomerServiceImpl() {

        customerMap = new HashMap<>();

        final Customer customerOne = new Customer("10A", "Jane", "ABC Company");
        final Customer customerTwo = new Customer("20B", "Bob", "XYZ Company");
        final Customer customerThree = new Customer("30C", "Tim", "CKV Company");

        customerMap.put("10A", customerOne);
        customerMap.put("20B", customerTwo);
        customerMap.put("30C", customerThree);

    }

    @Override
    public List<Customer> allCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerDetail(final String customerId) {
        return customerMap.get(customerId);
    }

}
