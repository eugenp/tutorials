package com.baeldung.examples.hexagonal;

import java.util.UUID;

public interface AccountService {
    UUID createAccount();

    Double deposit(UUID id, Double amount);

    Double withdrawal(UUID id, Double amount);

    Double getBalance(UUID id);
}

