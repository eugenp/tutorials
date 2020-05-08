package com.baeldung.hexagonal.banking.output.adapter;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Component;

import com.baeldung.hexagonal.banking.domain.Account;
import com.baeldung.hexagonal.banking.output.port.AccountStatePort;

@Component
public class AccountPersistenceAdapter implements AccountStatePort {

    private AccountRepository accountRepository;

    public AccountPersistenceAdapter(AccountRepository accountRepository) {
        super();
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> loadAccount(Long accountNumber) {
        AccountJpaEntity account = accountRepository.findById(accountNumber)
            .orElseThrow(EntityNotFoundException::new);

        return Optional.of( AccountMapper
            .mapToDomainEntity(account));
    }

    @Override
    public Account createOrUpdateAccount(Account account) {
        
        accountRepository.save(AccountMapper
            .mapToJpaEntity(account));
        
        return account;
    }

}

class AccountMapper {

    public static Account mapToDomainEntity(AccountJpaEntity account) {

        return new Account(account.getAccountNumber(), account.getAccountHolderIdNumber(), account.getFirstName(), account.getLastName(), account.getPin(), account.getBalance());
    }

    public static AccountJpaEntity mapToJpaEntity(Account account) {

        return new AccountJpaEntity(account.getAccountNumber(), account.getPin(), account.getBalance(), account.getIdNumber(), account.getFirstName(), account.getLastName());
    }

}
