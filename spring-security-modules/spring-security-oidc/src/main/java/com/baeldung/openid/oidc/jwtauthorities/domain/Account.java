package com.baeldung.openid.oidc.jwtauthorities.domain;

public class Account {
    
    private final Long id;
    private final String branch;
    private final String accountNumber;
    
    public Account(Long id, String branch, String accountNumber) {
        this.id = id;
        this.branch = branch;
        this.accountNumber = accountNumber;
    }
    
    public Long getId() {
        return id;
    }

    public String getBranch() {
        return branch;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
