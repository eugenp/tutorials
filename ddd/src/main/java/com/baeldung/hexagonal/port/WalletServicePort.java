package com.baeldung.hexagonal.port;

public interface WalletServicePort {
    boolean debit(String customerId, Double amount);
}
