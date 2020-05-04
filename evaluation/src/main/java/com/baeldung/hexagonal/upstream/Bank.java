package com.baeldung.hexagonal.upstream;

import java.util.LinkedHashMap;
import java.util.Optional;

import com.baeldung.hexagonal.banking.domain.Account;
import com.baeldung.hexagonal.banking.domain.AccountHolder;
import com.baeldung.hexagonal.banking.domain.CommercialAccount;
import com.baeldung.hexagonal.banking.domain.Company;
import com.baeldung.hexagonal.banking.domain.ConsumerAccount;
import com.baeldung.hexagonal.banking.domain.Person;

public class Bank implements BankPort {
    
    private Optional<LinkedHashMap<Long, Account>> accounts;

    public Bank() {
        
    }

    private Account getAccount(Long accountNumber) {
        return accounts.isPresent().get(accountNumber);
    }

    public Long openCommercialAccount(Company company, int pin, double startingDeposit) {

        Long newAccountId = null;
        if (givenDataIsValidToOpenAnAccount(company, pin, startingDeposit)) {
            newAccountId = getNextAccountId();
            CommercialAccount newCommercialAccount = new CommercialAccount(company, newAccountId, pin, startingDeposit);
            accounts.put(newAccountId, newCommercialAccount);
        }

        return newAccountId;
    }

    private boolean givenDataIsValidToOpenAnAccount(AccountHolder accountHolder, int pin, double startingDeposit) {
        return accountHolder != null && pin > 0 && startingDeposit >= 0.0d;
    }

    private Long getNextAccountId() {
        return Long.valueOf(accounts.size() + 1l);
    }

    public Long openConsumerAccount(Person person, int pin, double startingDeposit) {
        Long newAccountId = null;
        if (givenDataIsValidToOpenAnAccount(person, pin, startingDeposit)) {
            newAccountId = getNextAccountId();
            ConsumerAccount newConsumerAccount = new ConsumerAccount(person, newAccountId, pin, startingDeposit);
            accounts.put(newAccountId, newConsumerAccount);
        }

        return newAccountId;
    }

    private boolean accountDoesntExist(Long accountNumber) {
        return getAccount(accountNumber) == null;
    }

    public boolean authenticateUser(Long accountNumber, int pin) {
        if (accountDoesntExist(accountNumber))
            return false;
        return getAccount(accountNumber)
            .validatePin(pin);
    }

    public double getBalance(Long accountNumber) {

        if (accountDoesntExist(accountNumber))
            return 0.0D;
        return getAccount(accountNumber)
            .getBalance();
    }

    public void credit(Long accountNumber, double amount) {
        if (!accountDoesntExist(accountNumber)) {
            getAccount(accountNumber)
                .creditAccount(amount);
        }

    }

    public boolean debit(Long accountNumber, double amount) {
        if (accountDoesntExist(accountNumber))
            return false;
        return getAccount(accountNumber)
            .debitAccount(amount);

    }
}
