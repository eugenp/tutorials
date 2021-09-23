package com.hexagonal.adapter;

import com.hexagonal.adapter.entity.Account;
import com.hexagonal.adapter.mappers.AccountMapper;
import com.hexagonal.adapter.repository.AccountRepository;
import com.hexagonal.domain.AccountDto;
import com.hexagonal.ports.spi.AccountPersistencePort;
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
