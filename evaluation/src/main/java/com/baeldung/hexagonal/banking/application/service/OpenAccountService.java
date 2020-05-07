package com.baeldung.hexagonal.banking.application.service;

import java.math.BigDecimal;

import com.baeldung.hexagonal.banking.domain.Account;
import com.baeldung.hexagonal.banking.input.port.OpenAccontUseCasePort;
import com.baeldung.hexagonal.banking.input.port.OpenAccountCommand;
import com.baeldung.hexagonal.banking.output.port.AccountStatePort;

public class OpenAccountService implements OpenAccontUseCasePort {

    private AccountStatePort createAccountPort;

    public OpenAccountService(AccountStatePort createAccountPort) {
        super();
        this.createAccountPort = createAccountPort;
    }

    @Override
    public Long openAccount(OpenAccountCommand command) {

        if (!givenDataIsValidToOpenAnAccount(command.getPin(), command.getStartingDeposit())) 
            throw new InvalidDataException(command.getPin(), command.getStartingDeposit());
        
        Account newAccount = new Account(command.getAccountHolder(), command.getPin(), command.getStartingDeposit());
        return createAccountPort.createOrUpdateAccount(newAccount)
            .getAccountNumber();
    

    }

    private boolean givenDataIsValidToOpenAnAccount(int pin, BigDecimal startingDeposit) {
        return pin > 0 && startingDeposit.compareTo(BigDecimal.ZERO) >= 0;
    }

}
