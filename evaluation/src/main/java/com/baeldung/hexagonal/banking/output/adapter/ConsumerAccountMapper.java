package com.baeldung.hexagonal.banking.output.adapter;

import com.baeldung.hexagonal.banking.domain.Account;
import com.baeldung.hexagonal.banking.domain.ConsumerAccount;
import com.baeldung.hexagonal.banking.domain.Person;

public class ConsumerAccountMapper implements AccountMapper {

    @Override
    public Account mapToDomainEntity(AccountJpaEntity account) {
        Person company = new Person(account.getFirstName(), account.getLastName(), account.getAccountHolderIdNumber());

        return new ConsumerAccount(account.getAccountNumber(), company, account.getPin(), account.getBalance());
    }

    @Override
    public AccountJpaEntity mapToJpaEntity(Account account) {

        return new AccountJpaEntity(account.getAccountNumber(), 
                                    account.getPin(), 
                                    account.getBalance(), 
                                    "Consumer", 
                                    account.getAccountHolder().getIdNumber(), 
                                    ((Person) account.getAccountHolder()).getFirstName(),
                                    ((Person) account.getAccountHolder()).getLastName());
    }

}
