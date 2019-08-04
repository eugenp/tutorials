package com.hexagonal.arch.examples.core.interfaces;

import com.hexagonal.arch.examples.core.domain.CustomerCriteria;

public interface ICustomerCreationPort {
    String createCustomerId(CustomerCriteria criteria);
}
