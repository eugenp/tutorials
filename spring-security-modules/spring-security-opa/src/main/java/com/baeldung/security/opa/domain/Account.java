package com.baeldung.security.opa.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Account {

    private String id;
    private BigDecimal balance;
    private String currency;

    public static Account of(String id, BigDecimal balance, String currency) {
        Account account = new Account();
        account.setId(id);
        account.setBalance(balance);
        account.setCurrency(currency);
        return account;
    }

}
