package com.baeldung.hexagonal.application.services;

import com.baeldung.hexagonal.application.domain.BankAccount;
import com.baeldung.hexagonal.application.port.incoming.DepositUseCase;
import com.baeldung.hexagonal.application.port.incoming.WithdrawUseCase;
import com.baeldung.hexagonal.application.port.outgoing.LoadAccountPort;
import com.baeldung.hexagonal.application.port.outgoing.SaveAccountPort;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

public class BankAccountService implements DepositUseCase, WithdrawUseCase {

    private LoadAccountPort loadAccountPort;
    private SaveAccountPort saveAccountPort;

    public BankAccountService(LoadAccountPort loadAccountPort, SaveAccountPort saveAccountPort) {
        this.loadAccountPort = loadAccountPort;
        this.saveAccountPort = saveAccountPort;
    }

    @Override
    public void deposit(Long id, BigDecimal amount) {
        BankAccount account = loadAccountPort.load(id)
                .orElseThrow(NoSuchElementException::new);

        account.deposit(amount);

        saveAccountPort.save(account);
    }

    @Override
    public boolean withdraw(Long id, BigDecimal amount) {
        BankAccount account = loadAccountPort.load(id)
                .orElseThrow(NoSuchElementException::new);

        boolean hasWithdrawn = account.withdraw(amount);

        if(hasWithdrawn) {
            saveAccountPort.save(account);
        }
        return hasWithdrawn;
    }
}
