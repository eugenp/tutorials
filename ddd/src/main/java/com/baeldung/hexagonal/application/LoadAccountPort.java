package com.baeldung.hexagonal.application;

import java.util.Optional;

import com.baeldung.hexagonal.domain.BankAccount;

public interface LoadAccountPort {
    Optional<BankAccount> load(Long id);
}