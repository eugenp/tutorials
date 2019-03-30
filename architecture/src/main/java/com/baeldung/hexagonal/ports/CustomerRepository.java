package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.core.Customer;

public interface CustomerRepository {

    public Customer findBy(Long id);

}
