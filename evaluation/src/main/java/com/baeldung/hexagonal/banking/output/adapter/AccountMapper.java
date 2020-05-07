package com.baeldung.hexagonal.banking.output.adapter;

import com.baeldung.hexagonal.banking.domain.Account;
import com.baeldung.hexagonal.banking.domain.AccountHolder;

public class AccountMapper {

    public static Account mapToDomainEntity(AccountJpaEntity account) {
        AccountHolder holder = new AccountHolder(account.getAccountHolderIdNumber(), account.getFirstName(), account.getLastName());

        return new Account(account.getAccountNumber(), holder, account.getPin(), account.getBalance());
    }

    public static AccountJpaEntity mapToJpaEntity(Account account) {

        return new AccountJpaEntity(account.getAccountNumber(), 
                                    account.getPin(), 
                                    account.getBalance(), 
                                    account.getAccountHolder().getIdNumber(), 
                                    account.getAccountHolder().getFirstName(),
                                    account.getAccountHolder().getLastName());
    }

}
