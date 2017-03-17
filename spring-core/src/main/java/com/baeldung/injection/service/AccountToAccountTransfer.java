package com.baeldung.injection.service;

import java.math.BigDecimal;

import com.baeldung.injection.consumer.IAccountTransaction;

public class AccountToAccountTransfer implements IAccountTransaction {
    public void validateTransaction(BigDecimal amount) {
        System.out.println("Transaction from your account to the sender account number in same bank");

    }

}
