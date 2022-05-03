package com.baeldung.security.opa.domain;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Account {
    
    private String id;
    private BigDecimal balance;
    private String currency;
    
    
    public static Account of(String id, BigDecimal balance, String currency) {
        Account acc = new Account();
        acc.setId(id);
        acc.setBalance(balance);
        acc.setCurrency(currency);
        
        return acc;
    }


}
