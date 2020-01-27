package com.baeldung.domain;

public interface IAccountingService {

    public Account getAccount();
    public Double getBalance(int accountId);
}
