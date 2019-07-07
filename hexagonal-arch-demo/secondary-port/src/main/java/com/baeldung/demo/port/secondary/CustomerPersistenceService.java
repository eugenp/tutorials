package com.baeldung.demo.port.secondary;

import com.baeldung.demo.model.Customer;

public interface CustomerPersistenceService {
    Customer createCustomer(Customer customer);
    Customer retrieveCustomer(String customerId);
    Customer updateCustomer(Customer customer);
}
