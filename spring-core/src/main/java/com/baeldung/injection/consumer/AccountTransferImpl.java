package com.baeldung.injection.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountTransferImpl implements IAccountTransfer {

    private IAccountTransaction transaction;

    // setter-based dependency injection
    @Autowired
    public void setTransaction(IAccountTransaction transaction) {
        this.transaction = transaction;
    }
    // constructor-based dependency injection
    /* @Autowired
     public AccountTransferImpl(AccountTransaction transaction){
         this.transaction=transaction;
     }*/

    public boolean deposit(BigDecimal amount) {
        transaction.validateTransaction(amount);
        return true;
    }

    public boolean withdraw(BigDecimal amount) {
        transaction.validateTransaction(amount);
        return true;

    }

}
