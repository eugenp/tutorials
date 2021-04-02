package com.baeldung.hexagonal.external.service;

import org.springframework.http.HttpStatus;

public interface CustomerWalletService {

    HttpStatus postDebit(String customerId, Double amount);
}
