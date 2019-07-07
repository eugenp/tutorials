package com.baeldung.demo.port.primary;

import com.baeldung.demo.model.Customer;

public interface CustomerService {
    Customer createCustomer(Customer customer);
    Customer updateCustomerAddress(Customer customer);
    boolean isFreeShippingEligible(String customerId);
    Customer getCustomer(String customerId);
}
