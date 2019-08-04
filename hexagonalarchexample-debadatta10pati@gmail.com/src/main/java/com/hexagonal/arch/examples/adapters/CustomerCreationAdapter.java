package com.hexagonal.arch.examples.adapters;


import org.springframework.beans.factory.annotation.Autowired;

import com.hexagonal.arch.examples.core.domain.CustomerCriteria;
import com.hexagonal.arch.examples.core.interfaces.ICustomerCreationPort;
import com.hexagonal.arch.examples.service.CustomerCreationService;


public class CustomerCreationAdapter implements ICustomerCreationPort {

    @Autowired
    private CustomerCreationService customerCreationService;
    
    @Override
    public String createCustomerId(CustomerCriteria criteria) {

        return customerCreationService.createCustomerId(criteria);
    }
}
