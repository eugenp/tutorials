package com.baeldung.hex.demo.port.primary;

import com.baeldung.hex.demo.core.model.Customer;

public interface CustomerService {
        Customer createCustomer(Customer customer);

        Customer updateCustomerAddress(Customer customer);

        boolean isFreeShippingEligible(String customerId);

        Customer getCustomer(String customerId);
}
