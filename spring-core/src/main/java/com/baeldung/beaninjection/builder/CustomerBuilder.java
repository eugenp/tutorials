package com.baeldung.beaninjection.builder;

import com.baeldung.beaninjection.model.Customer;

public class CustomerBuilder {
    public Customer getCustomer(int customerId) {
        Customer customer = new Customer(); // customer to be fetched from backend
        customer.setName("Customer Name");
        customer.setCustomerId(customerId);
        customer.setContactInfo("Contact Information of Customer");
        customer.setEmail("customer@abc.com");
        return customer;
    }
}
