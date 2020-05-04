package com.baeldung.hexagonal.banking.application.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.banking.domain.Account;
import com.baeldung.hexagonal.banking.input.port.TransferMoneyCommand;
import com.baeldung.hexagonal.banking.input.port.TransferMoneyUseCasePort;
import com.baeldung.hexagonal.banking.output.port.LoadAccountPort;
import com.baeldung.hexagonal.banking.output.port.UpdateAccountStatePort;

@Component
@Transactional
public class TransferMoneyService implements TransferMoneyUseCasePort {

    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountStatePort updateAccountStatePort;

    public TransferMoneyService(LoadAccountPort loadAccountPort, UpdateAccountStatePort updateAccountStatePort) {
        super();
        this.loadAccountPort = loadAccountPort;
        this.updateAccountStatePort = updateAccountStatePort;
    }

    @Override
    public void transferMoney(TransferMoneyCommand command) throws InvalidPinException, NotEnoughBalanceException{


        Account sourceAccount = loadAccountPort.loadAccount(command.getSourceAccountNumber())
            .get();

        if (!sourceAccount.validatePin(command.getAttemptedPin())) {
            throw new InvalidPinException();
        }
        
        if (!sourceAccount.debitAccount(command.getAmount())) {
            throw new NotEnoughBalanceException();
        }
        
        Account targetAccount = loadAccountPort.loadAccount(command.getTargetAccountNumber())
            .get();


        targetAccount.creditAccount(command.getAmount());

        updateAccountStatePort.createOrUpdateAccount(sourceAccount);
        updateAccountStatePort.createOrUpdateAccount(targetAccount);

           
    }
}
