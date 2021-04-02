package com.baeldung.hexagonal.external.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class MockCustomerWalletService implements CustomerWalletService {
    public HttpStatus postDebit(String customerId, Double amount) {
        return HttpStatus.CREATED;
    }
}
