package com.baeldung.hexagonal;

public interface IBankOutputPort {
        Double getBalance(String accountNumber);

        void updateBalance(String accountNumber, Double amount);
}
