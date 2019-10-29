package com.baeldung.dip.application;

import com.baeldung.dip.daoimplementations.SimpleCustomerDao;
import com.baeldung.dip.entities.Customer;
import com.baeldung.dip.services.CustomerService;
import java.util.Map;
import java.util.HashMap;

public class Application {

    public static void main(String[] args) {
        Map<Integer, Customer> customers = new HashMap<>();
        customers.put(1, new Customer("John"));
        customers.put(2, new Customer("Susan"));
        CustomerService customerService = new CustomerService(new SimpleCustomerDao(customers));
        customerService.findAll().forEach(System.out::println);
    }
}
