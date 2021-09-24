package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.adapter.entity.Account;
import com.baeldung.hexagonal.adapter.mappers.AccountMapper;
import com.baeldung.hexagonal.adapter.repository.AccountRepository;
import com.baeldung.hexagonal.domain.AccountDto;
import com.baeldung.hexagonal.ports.spi.AccountPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountJpaAdapter implements AccountPersistencePort {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountDto addAccount(AccountDto accountDto) {
        Account account = AccountMapper.INSTANCE.accountDtoToAccount(accountDto);
        Account accountSaved = accountRepository.save(account);
        return AccountMapper.INSTANCE.accountToAccountDto(accountSaved);
    }
}
