package com.account.service.repository;

import com.account.service.domain.Account;
import com.account.service.repository.command.InsertAccountCommand;
import com.account.service.repository.command.UpdateAccountCommand;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
  List<Account> findAll();

  Optional<Account> findById(final Long id);

  Optional<Account> findByAccountId(final String accountId);

  Account update(final UpdateAccountCommand command);

  Account save(final InsertAccountCommand command);

  void delete(final Long id);
}
