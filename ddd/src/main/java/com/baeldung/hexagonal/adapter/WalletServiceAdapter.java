package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.external.service.CustomerWalletService;
import com.baeldung.hexagonal.port.WalletServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.http.HttpStatus.CREATED;

@Component
public class WalletServiceAdapter implements WalletServicePort {

    @Autowired
    private CustomerWalletService customerWalletService;

    public boolean debit(String customerId, Double amount) {
        return customerWalletService.postDebit(customerId, amount) == CREATED;
    }
}
