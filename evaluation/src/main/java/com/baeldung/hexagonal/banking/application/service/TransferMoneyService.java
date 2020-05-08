package com.baeldung.hexagonal.banking.application.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.banking.domain.Account;
import com.baeldung.hexagonal.banking.input.port.TransferMoneyCommand;
import com.baeldung.hexagonal.banking.input.port.TransferMoneyPort;
import com.baeldung.hexagonal.banking.output.port.AccountStatePort;

@Component
@Transactional
public class TransferMoneyService implements TransferMoneyPort {

    private AccountStatePort accountStatePort;

    public TransferMoneyService(AccountStatePort accountStatePort) {
        super();
        this.accountStatePort = accountStatePort;
    }

    @Override
    public Boolean transferMoney(TransferMoneyCommand command){


        Account sourceAccount = accountStatePort.loadAccount(command.getSourceAccountNumber())
            .get();

        if (!sourceAccount.validatePin(command.getAttemptedPin())) {
            return Boolean.FALSE;
        }
        
        if (!sourceAccount.debitAccount(command.getAmount())) {
            return Boolean.FALSE;
        }
        
        Account targetAccount = accountStatePort.loadAccount(command.getTargetAccountNumber())
            .get();


        targetAccount.creditAccount(command.getAmount());

        accountStatePort.createOrUpdateAccount(sourceAccount);
        accountStatePort.createOrUpdateAccount(targetAccount);
        
        return Boolean.TRUE;

           
    }
}
