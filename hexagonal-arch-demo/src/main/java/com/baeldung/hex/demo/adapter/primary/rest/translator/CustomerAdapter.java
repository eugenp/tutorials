package com.baeldung.hex.demo.adapter.primary.rest.translator;

import com.baeldung.hex.demo.adapter.primary.rest.resource.CustomerResource;
import com.baeldung.hex.demo.core.model.Customer;

public class CustomerAdapter {
    public Customer toDomain(CustomerResource resource) {
        return new Customer();
    }
    public CustomerResource toResource(Customer domain) {
        return new CustomerResource();
    }
}
