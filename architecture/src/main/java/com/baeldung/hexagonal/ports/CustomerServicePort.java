package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.core.Customer;

public interface CustomerServicePort {

    public Customer findCustomerBy(Long id);
}
