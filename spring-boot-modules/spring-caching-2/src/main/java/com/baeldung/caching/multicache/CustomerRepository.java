package com.baeldung.caching.multicache;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerRepository {

    private final Map<String, Customer> customerMap = new HashMap<>();

    public Customer getCustomerById(String id) {
        return customerMap.get(id);
    }

    @PostConstruct
    private void setupCustomerRepo() {
        Customer product1 = getCustomer("100001", "name1", "name1@mail.com");
        customerMap.put("100001", product1);

        Customer product2 = getCustomer("100002", "name2", "name2@mail.com");
        customerMap.put("100002", product2);

        Customer product3 = getCustomer("100003", "name3", "name3@mail.com");
        customerMap.put("100003", product3);

        Customer product4 = getCustomer("100004", "name4", "name4@mail.com");
        customerMap.put("100004", product4);

        Customer product5 = getCustomer("100005", "name5", "name5@mail.com");
        customerMap.put("100005", product5);
    }

    private static Customer getCustomer(String id, String name, String email) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setEmail(email);

        return customer;
    }
}
