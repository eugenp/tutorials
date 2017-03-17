package com.baeldung.noinjection.service;

import java.math.BigDecimal;

import com.baeldung.noinjection.consumer.AccountTransaction;

public class AccountToLocalBankTransfer implements AccountTransaction {

    public void validateTransaction(BigDecimal amount) {
        System.out.println("Transaction from your account to the sender account number in other local bank");

    }

}
