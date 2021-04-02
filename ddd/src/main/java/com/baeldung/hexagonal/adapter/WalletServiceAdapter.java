package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.external.service.CustomerWalletService;
import com.baeldung.hexagonal.port.WalletServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WalletServiceAdapter implements WalletServicePort {

    private final CustomerWalletService customerWalletService;

    @Autowired
    public WalletServiceAdapter(CustomerWalletService customerWalletService) {
        this.customerWalletService = customerWalletService;
    }

    public boolean debit(String customerId, Double amount) {
        HttpStatus response = customerWalletService.postDebit(customerId, amount);
        return response == HttpStatus.CREATED;
    }
}
