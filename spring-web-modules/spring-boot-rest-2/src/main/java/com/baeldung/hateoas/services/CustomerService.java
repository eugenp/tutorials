package com.baeldung.hateoas.services;

import java.util.List;

import com.baeldung.hateoas.persistence.model.Customer;

public interface CustomerService {

    List<Customer> allCustomers();

    Customer getCustomerDetail(final String id);

}
