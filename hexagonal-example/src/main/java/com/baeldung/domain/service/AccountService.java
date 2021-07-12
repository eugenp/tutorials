package com.baeldung.domain.service;

import java.math.BigDecimal;

import com.baeldung.domain.model.Account;
import com.baeldung.domain.port.incoming.Deposit;
import com.baeldung.domain.port.incoming.Withdraw;
import com.baeldung.domain.port.outgoing.RetrieveAccount;
import com.baeldung.domain.port.outgoing.PersistAccount;

public class AccountService implements Deposit, Withdraw {

    private RetrieveAccount retrieveAccount;
    private PersistAccount persistAccount;

    public AccountService(RetrieveAccount retrieveAccount, PersistAccount persistAccount) {
        this.retrieveAccount = retrieveAccount;
        this.persistAccount = persistAccount;
    }

    @Override
    public void deposit(Long accountNo, BigDecimal depositAmount) {
        Account account = retrieveAccount.load(accountNo);
        account.depositAmount(depositAmount);
        persistAccount.save(account);
    }

    @Override
    public boolean withdraw(Long accountNo, BigDecimal withdrawalAmount) {
        Account account = retrieveAccount.load(accountNo);
        boolean withdrawSucess = account.withdrawAmount(withdrawalAmount);

        if (withdrawSucess) {
            persistAccount.save(account);
        }
        return withdrawSucess;
    }
}