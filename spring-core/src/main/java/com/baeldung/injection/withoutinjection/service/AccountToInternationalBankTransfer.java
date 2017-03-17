package com.baeldung.injection.withoutinjection.service;

import java.math.BigDecimal;

import com.baeldung.injection.withoutinjection.consumer.AccountTransaction;

public class AccountToInternationalBankTransfer implements AccountTransaction {

    public void validateTransaction(BigDecimal amount) {
        System.out.println("Transaction from your account to the sender account number in other international bank");

    }

}
