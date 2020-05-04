package com.baeldung.hexagonal.banking.application.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.banking.input.port.GetAccountBalanceQueryPort;
import com.baeldung.hexagonal.banking.output.port.LoadAccountPort;

@Component
public class GetAccountBalanceService implements GetAccountBalanceQueryPort{

    private final LoadAccountPort loadAccountPort;
    
    public GetAccountBalanceService(LoadAccountPort loadAccountPort) {
        super();
        this.loadAccountPort = loadAccountPort;
    }

    @Override
    public BigDecimal getBalance(Long accountNumber) {
        return loadAccountPort.loadAccount(accountNumber)
            .getBalance();
    }

}
