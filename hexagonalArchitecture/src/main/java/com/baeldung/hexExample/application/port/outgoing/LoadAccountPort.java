package com.baeldung.hexExample.application.port.outgoing;


import com.baeldung.hexExample.application.domain.BankAccount;

import java.util.Optional;

public interface LoadAccountPort {
    Optional<BankAccount> load(Long id);
}