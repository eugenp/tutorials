package com.baeldung.hexagonal.banking.output.adapter;

import com.baeldung.hexagonal.banking.domain.Account;
import com.baeldung.hexagonal.banking.domain.CommercialAccount;
import com.baeldung.hexagonal.banking.domain.Company;

public class CommercialAccountMapper implements AccountMapper {

    @Override
    public Account mapToDomainEntity(AccountJpaEntity account) {
        Company company = new Company(account.getCompanyName(), account.getAccountHolderIdNumber());

        return new CommercialAccount(account.getAccountNumber(), company, account.getPin(), account.getBalance());
    }

    @Override
    public AccountJpaEntity mapToJpaEntity(Account account) {

        return new AccountJpaEntity(account.getAccountNumber(), 
                                    account.getPin(), 
                                    account.getBalance(), 
                                    "Commercial", 
                                    account.getAccountHolder().getIdNumber(), 
                                    ((Company) account.getAccountHolder()).getCompanyName());
    }

}
