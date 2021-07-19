package com.baeldung.dip.mainapp;

import com.baeldung.dip.daoimplementations.MapCustomerDao;
import com.baeldung.dip.entities.Customer;
import com.baeldung.dip.services.CustomerService;
import java.util.HashMap;

public class MainApplication {

    public static void main(String args[]) {
        var customers = new HashMap<Integer, Customer>();
        customers.put(1, new Customer("John"));
        customers.put(2, new Customer("Susan"));
        CustomerService customerService = new CustomerService(new SimpleCustomerDao(customers));
        customerService.findAll().forEach(System.out::println);
    }
}
