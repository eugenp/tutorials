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
public class TransferMoneyService implements TransferMoneyUseCasePort{

    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountStatePort updateAccountStatePort;
    
    public TransferMoneyService(LoadAccountPort loadAccountPort, UpdateAccountStatePort updateAccountStatePort) {
        super();
        this.loadAccountPort = loadAccountPort;
        this.updateAccountStatePort = updateAccountStatePort;
    }

    @Override
    public boolean transferMoney(TransferMoneyCommand command) {
        
            Account sourceAccount = loadAccountPort.loadAccount(
                            command.getSourceAccountNumber());

            Account targetAccount = loadAccountPort.loadAccount(
                            command.getTargetAccountNumber());

            
           if(!sourceAccount.validatePin(command.getAttemptedPin())) {
               throw new InvalidPinException();
           }
           // AccountId sourceAccountId = sourceAccount.getId()
           //                 .orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
           // AccountId targetAccountId = targetAccount.getId()
           //                 .orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));

            if (!sourceAccount.debitAccount(command.getAmount())) {
               throw new NotEnoughBalanceException();
            }
            
            targetAccount.creditAccount(command.getAmount());
            
            updateAccountStatePort.createOrUpdateAccount(sourceAccount);
            updateAccountStatePort.createOrUpdateAccount(targetAccount);

            return true;
    }

}
