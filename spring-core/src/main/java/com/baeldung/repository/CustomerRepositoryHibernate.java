package com.baeldung.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.baeldung.model.Customer;

@Repository
public class CustomerRepositoryHibernate implements CustomerRepository {

    public List<Customer> findAll() {

        List<Customer> customers = new ArrayList<Customer>();
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");

        customers.add(customer);

        return customers;
    }

}
