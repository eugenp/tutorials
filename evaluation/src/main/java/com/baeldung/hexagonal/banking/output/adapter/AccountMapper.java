package com.baeldung.hexagonal.banking.output.adapter;

import com.baeldung.hexagonal.banking.domain.Account;

public class AccountMapper {

    public static Account mapToDomainEntity(AccountJpaEntity account) {

        return new Account(account.getAccountNumber(), account.getAccountHolderIdNumber(), account.getFirstName(), account.getLastName(), account.getPin(), account.getBalance());
    }

    public static AccountJpaEntity mapToJpaEntity(Account account) {

        return new AccountJpaEntity(account.getAccountNumber(), account.getPin(), account.getBalance(), account.getIdNumber(), account.getFirstName(), account.getLastName());
    }

}
