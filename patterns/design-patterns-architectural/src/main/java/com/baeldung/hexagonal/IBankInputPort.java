package com.baeldung.hexagonal;

public interface IBankInputPort {
    public void credit(String accountNumber, Double amount);
    public void debit(String accountNumber, Double amount);
}
