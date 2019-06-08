package com.baeldung.domain.port;

import com.baeldung.domain.entity.Customer;

public interface CustomerPort {
    Customer getCustomerById(long id);
}
