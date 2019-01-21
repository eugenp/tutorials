package com.baeldung.tutorial.hexagonal.storage;

import com.baeldung.tutorial.hexagonal.domain.BankAccount;

import java.util.Map;

class InMemoryBankAccountRepository implements BankAccountRepository {
        private Map<String, BankAccount> bankAccounts;

        public BankAccount findByBankAccountId(String bankAccountId) {
                return bankAccounts.get(bankAccountId);
        }

        public void saveAccount(BankAccount bankAccount) {
                bankAccounts.putIfAbsent(bankAccount.getAccountId(), bankAccount);
        }
}
