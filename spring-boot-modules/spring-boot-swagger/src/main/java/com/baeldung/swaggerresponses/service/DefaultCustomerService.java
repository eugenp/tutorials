package com.baeldung.swaggerresponses.service;

import com.baeldung.swaggerresponses.response.CustomerResponse;

import org.springframework.stereotype.Service;

@Service
public class DefaultCustomerService implements CustomerService {
    @Override
    public CustomerResponse getById(Long id) {
        return new CustomerResponse(1L, "Jane", "Doe");
    }
}
