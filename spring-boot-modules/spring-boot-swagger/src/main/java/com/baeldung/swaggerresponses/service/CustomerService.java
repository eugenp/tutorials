package com.baeldung.swaggerresponses.service;

import com.baeldung.swaggerresponses.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse getById(Long id);
}
