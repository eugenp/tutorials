package com.baeldung.hexagonal.banking.application.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.banking.input.port.GetAccountBalanceQueryPort;
import com.baeldung.hexagonal.banking.output.port.AccountStatePort;

@Component
public class GetAccountBalanceService implements GetAccountBalanceQueryPort{

    private AccountStatePort accountStatePort;
    
    public GetAccountBalanceService(AccountStatePort accountStatePort) {
        super();
        this.accountStatePort = accountStatePort;
    }

    @Override
    public BigDecimal getBalance(Long accountNumber) {
        return accountStatePort.loadAccount(accountNumber).get()
            .getBalance();
    }

}
