package com.baeldung.services;

import java.util.List;

import com.baeldung.persistence.model.Customer;

public interface CustomerService {

    List<Customer> allCustomers();

    Customer getCustomerDetail(final String id);

}
