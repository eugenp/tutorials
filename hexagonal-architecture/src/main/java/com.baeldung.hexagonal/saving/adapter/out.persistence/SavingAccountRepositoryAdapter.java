package com.baeldung.hexagonal.saving.adapter.out.persistence;

import com.baeldung.hexagonal.saving.application.port.out.SavingAccountRepository;
import com.baeldung.hexagonal.saving.domain.SavingAccount;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class SavingAccountRepositoryAdapter implements SavingAccountRepository {

    private List<SavingAccount> savingAccounts = new ArrayList<>();
    private Long nextAvailableId = 1L;

    @Override
    public Long addSavingAccount(SavingAccount savingAccount) {
        savingAccount.setId(nextAvailableId);
        savingAccounts.add(savingAccount);
        incrementNextAvailableId();
        return savingAccount.getId();
    }

    @Override
    public List<SavingAccount> getSavingAccounts() {
        return savingAccounts;
    }

    @Override
    public BigDecimal getBalance(String accountNumber) {

        Iterator<SavingAccount> iterator = savingAccounts.iterator();
        while (iterator.hasNext()) {
            SavingAccount savingAccount = iterator.next();
            if (savingAccount.getAccountNumber().equals(accountNumber)) {
                return savingAccount.getBalance();
            }
        }

        return null;
    }

    private void incrementNextAvailableId() {
        nextAvailableId++;
    }
}
