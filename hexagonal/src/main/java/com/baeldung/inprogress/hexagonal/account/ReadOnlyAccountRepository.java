package com.baeldung.inprogress.hexagonal.account;

import java.util.Optional;

public interface ReadOnlyAccountRepository {
    Optional<Account> getAccountById(String id);
}
