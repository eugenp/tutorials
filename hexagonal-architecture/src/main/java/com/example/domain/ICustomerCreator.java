package com.example.domain;

import com.example.application.CustomerRequest;

public interface ICustomerCreator {
    CustomerId save(CustomerRequest customer);
}
