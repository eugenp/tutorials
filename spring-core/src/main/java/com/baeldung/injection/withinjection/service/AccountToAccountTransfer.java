package com.baeldung.injection.withinjection.service;

import java.math.BigDecimal;

import com.baeldung.injection.withinjection.consumer.AccountTransaction;

public class AccountToAccountTransfer implements AccountTransaction {
    public void validateTransaction(BigDecimal amount) {
        System.out.println("Transaction from your account to the sender account number in same bank");

    }

}
