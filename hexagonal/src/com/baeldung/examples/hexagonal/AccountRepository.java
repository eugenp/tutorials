package com.baeldung.examples.hexagonal;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository {

    Optional<Account> findById(UUID id);

    Account save(Account account);
}
