package com.baeldung.dip.application;

import com.baeldung.dip.daoimplementations.ListCustomerDao;
import com.baeldung.dip.entities.Customer;
import com.baeldung.dip.daointerfaces.CustomerDao;
import com.baeldung.dip.services.CustomerService;
import java.util.Arrays;

public class Application {

    public static void main(String[] args) {
        CustomerDao customerDao = new ListCustomerDao(Arrays.asList(new Customer(1, "John"), new Customer(2, "Susan")));
        CustomerService customerService = new CustomerService(customerDao);
        customerService.findAll().forEach(System.out::println);
        System.out.println(customerService.findById(0).orElseGet(() -> new Customer(0, "Non- existing customer")));
    }
}
