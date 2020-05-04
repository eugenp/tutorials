package com.baeldung.hexagonal.banking.output.adapter;

import com.baeldung.hexagonal.banking.domain.Account;

public interface AccountMapper {

    Account mapToDomainEntity(AccountJpaEntity account);

    AccountJpaEntity mapToJpaEntity(Account activity);

}
