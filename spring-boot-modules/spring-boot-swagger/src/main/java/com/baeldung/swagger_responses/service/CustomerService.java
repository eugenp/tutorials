package com.baeldung.swagger_responses.service;

import com.baeldung.swagger_responses.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse getById(Long id);
}
