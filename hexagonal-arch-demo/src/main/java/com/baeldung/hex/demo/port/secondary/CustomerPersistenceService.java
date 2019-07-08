package com.baeldung.hex.demo.port.secondary;

import com.baeldung.hex.demo.core.model.Customer;

public interface CustomerPersistenceService {
        Customer createCustomer(Customer customer);

        Customer retrieveCustomer(String customerId);

        Customer updateCustomer(Customer customer);
}
