package com.baeldung.hexagonal.application.repository;

import com.baeldung.hexagonal.application.LoadAccountPort;
import com.baeldung.hexagonal.application.SaveAccountPort;

public interface BankAccountRepository extends LoadAccountPort, SaveAccountPort {
}