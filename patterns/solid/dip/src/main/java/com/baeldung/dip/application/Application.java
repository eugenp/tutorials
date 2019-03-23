package com.baeldung.dip.application;

import com.baeldung.dip.daoimplementations.MapCustomerDao;
import com.baeldung.dip.entities.Customer;
import com.baeldung.dip.services.CustomerService;
import java.util.HashMap;

public class Application {

    public static void main(String[] args) {
        var customers = new HashMap<Integer, Customer>();
        customers.put(1, new Customer("John"));
        customers.put(2, new Customer("Susan"));
        CustomerService customerService = new CustomerService(new MapCustomerDao(customers));
        customerService.findAll().values().forEach(System.out::println);
    }
}
