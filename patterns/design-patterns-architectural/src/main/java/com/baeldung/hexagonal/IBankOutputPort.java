package com.baeldung.hexagonal;

public interface IBankOutputPort {
    public Double getBalance(String accountNumber);
    public void updateBalance(String accountNumber, Double amount);
}
