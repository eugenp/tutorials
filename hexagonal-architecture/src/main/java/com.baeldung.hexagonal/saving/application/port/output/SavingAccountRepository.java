package com.baeldung.hexagonal.saving.application.port.out;

import com.baeldung.hexagonal.saving.domain.SavingAccount;

import java.math.BigDecimal;
import java.util.List;

public interface SavingAccountRepository {
    Long addSavingAccount(SavingAccount savingAccount);

    List<SavingAccount> getSavingAccounts();

    BigDecimal getBalance(String accountNumber);
}
