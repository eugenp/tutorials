package org.baeldung.web.service;

import java.util.List;

import org.baeldung.model.Customer;

public interface CustomerService {

     List<Customer> allCustomers();

     Customer getCustomerDetail(final String id);

}
