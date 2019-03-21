package com.baeldung.dipmodular.mainapp;

import com.baeldung.dipmodular.daoimplementations.ListCustomerDao;
import com.baeldung.dipmodular.daos.CustomerDao;
import com.baeldung.dipmodular.entities.Customer;
import com.baeldung.dipmodular.services.CustomerService;
import java.util.Arrays;

public class MainApplication {

    public static void main(String args[]) {
        CustomerDao customerDao = new ListCustomerDao(Arrays.asList(new Customer(1, "John"), new Customer(2, "Susan")));
        CustomerService customerService = new CustomerService(customerDao);
        customerService.findAll().forEach(System.out::println);
        System.out.println(customerService.findById(0).orElseGet(() -> new Customer(0, "Non- existing customer")));
    }
}
