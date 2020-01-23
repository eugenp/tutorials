package com.baeldung.hexagonal.application.port.outgoing;

import com.baeldung.hexagonal.application.domain.BankAccount;

import java.util.Optional;

public interface LoadAccountPort {
    Optional<BankAccount> load(Long id);
}