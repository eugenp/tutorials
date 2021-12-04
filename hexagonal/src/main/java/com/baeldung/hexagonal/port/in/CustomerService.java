package com.baeldung.hexagonal.port.in;

import com.baeldung.hexagonal.domain.model.Customer;

public interface CustomerService {

    Customer register(Customer customer);

    Customer getByUsername(String username);
}
