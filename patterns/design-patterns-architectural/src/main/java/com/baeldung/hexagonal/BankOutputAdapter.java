package com.baeldung.hexagonal;

public class BankOutputAdapter implements IBankOutputPort {
    @Override
    public Double getBalance(String accountNumber) {
        // gets balance from external source like database
        return 0d;
    }

    @Override
    public void updateBalance(String accountNumber, Double amount) {
        // updates balance in external source like database
    }
}
