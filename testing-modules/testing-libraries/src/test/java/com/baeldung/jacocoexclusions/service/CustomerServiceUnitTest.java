package com.baeldung.jacocoexclusions.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class CustomerServiceUnitTest {

    @Test
    public void givenCustomer_whenGetCustomer_thenReturnNewCustomer() {
        CustomerService customerService = new CustomerService();
        assertNotNull(customerService.getCustomerName());
    }
}
