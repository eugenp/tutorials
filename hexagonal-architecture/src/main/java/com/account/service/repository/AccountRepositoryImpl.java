package com.account.service.repository;

import com.account.service.domain.Account;
import com.account.service.repository.command.InsertAccountCommand;
import com.account.service.repository.command.UpdateAccountCommand;
import com.account.service.repository.jpa.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.account.service.repository.RepositoryConverter.toAccount;
import static com.account.service.repository.RepositoryConverter.toAccountEntity;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

  private final AccountJpaRepository accountJpaRepository;

  @Override
  public List<Account> findAll() {
    log.info("Find all accounts ");
    return accountJpaRepository.findAll().stream()
      .map(RepositoryConverter::toAccount)
      .collect(Collectors.toList());
  }

  @Override
  public Optional<Account> findById(final Long id) {
    log.info("Find account by id : {}", id);
    return accountJpaRepository.findById(id)
      .map(RepositoryConverter::toAccount);
  }

  @Override
  public Optional<Account> findByAccountId(final String accountId) {
    log.info("Find account by accountID : {}", accountId);
    return accountJpaRepository.findByAccountId(accountId)
      .map(RepositoryConverter::toAccount);
  }

  @Override
  public Account update(final UpdateAccountCommand command) {
    log.info("Update account by command : {}", command);
    return toAccount(accountJpaRepository.save(toAccountEntity(command)));
  }

  @Override
  public Account save(final InsertAccountCommand command) {
    log.info("Save account by command : {}", command);
    return toAccount(accountJpaRepository.save(toAccountEntity(command)));
  }

  @Override
  public void delete(final Long id) {
    log.info("Delete account by id : {}", id);
    accountJpaRepository.deleteById(id);
  }
}
