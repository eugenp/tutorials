package com.baeldung.domain.port;

import com.baeldung.domain.entity.Customer;

public interface CustomerRepositoryPort {
    Customer getCustomer();
}
