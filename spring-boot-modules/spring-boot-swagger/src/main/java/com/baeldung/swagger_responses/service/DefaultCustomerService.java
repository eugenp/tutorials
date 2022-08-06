package com.baeldung.swagger_responses.service;

import com.baeldung.swagger_responses.response.CustomerResponse;

import org.springframework.stereotype.Service;

@Service
public class DefaultCustomerService implements CustomerService {
    @Override
    public CustomerResponse getById(Long id) {
        return new CustomerResponse(1L, "Jane", "Doe");
    }
}
