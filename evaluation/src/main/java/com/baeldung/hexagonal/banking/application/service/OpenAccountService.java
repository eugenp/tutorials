package com.baeldung.hexagonal.banking.application.service;

import java.math.BigDecimal;

import com.baeldung.hexagonal.banking.domain.CommercialAccount;
import com.baeldung.hexagonal.banking.domain.ConsumerAccount;
import com.baeldung.hexagonal.banking.input.port.OpenAccontUseCasePort;
import com.baeldung.hexagonal.banking.input.port.OpenCommercialAccountCommand;
import com.baeldung.hexagonal.banking.input.port.OpenConsumerAccountCommand;
import com.baeldung.hexagonal.banking.output.port.AccountStatePort;

public class OpenAccountService implements OpenAccontUseCasePort {

    private AccountStatePort createAccountPort;

    public OpenAccountService(AccountStatePort createAccountPort) {
        super();
        this.createAccountPort = createAccountPort;
    }

    @Override
    public Long openCommercialAccount(OpenCommercialAccountCommand command) {

        if (!givenDataIsValidToOpenAnAccount(command.getPin(), command.getStartingDeposit())) 
            throw new InvalidDataException(command.getPin(), command.getStartingDeposit());
        
        CommercialAccount newCommercialAccount = new CommercialAccount(command.getAccountHolder(), command.getPin(), command.getStartingDeposit());
        return createAccountPort.createOrUpdateAccount(newCommercialAccount)
            .getAccountNumber();
    

    }

    @Override
    public Long openConsumerAccount(OpenConsumerAccountCommand command) {
        
        if (!givenDataIsValidToOpenAnAccount(command.getPin(), command.getStartingDeposit())) 
            throw new InvalidDataException(command.getPin(), command.getStartingDeposit());
            
        ConsumerAccount newConsumerAccount = new ConsumerAccount(command.getAccountHolder(), command.getPin(), command.getStartingDeposit());
        return createAccountPort.createOrUpdateAccount(newConsumerAccount)
            .getAccountNumber();
        
    }

    private boolean givenDataIsValidToOpenAnAccount(int pin, BigDecimal startingDeposit) {
        return pin > 0 && startingDeposit.compareTo(BigDecimal.ZERO) >= 0;
    }

}
