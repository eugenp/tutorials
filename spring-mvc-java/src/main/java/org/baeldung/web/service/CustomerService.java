package org.baeldung.web.service;

import java.util.List;

import org.baeldung.model.Customer;

public interface CustomerService {

    public List<Customer> allCustomers();

    public Customer getCustomerDetail(final String id);

}
