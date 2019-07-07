package com.baeldung.demo.port.secondary;

import com.baeldung.demo.model.Customer;

public interface CustomerPersistenceService {
    void createCustomer(Customer customer);
    Customer retrieveCustomer(String customerId);
    boolean updateCustomer(Customer customer);
}
