package com.account.service.service;

import com.account.service.domain.Account;
import com.account.service.domain.Transfer;
import com.account.service.service.command.CreateAccountCommand;
import com.account.service.service.command.SendMoneyCommand;

import javax.validation.Valid;
import java.util.List;

public interface AccountUseCase {
  Account retrieveAccountById(final Long id);

  List<Account> retrieveAccounts();

  Account create(final @Valid CreateAccountCommand command);

  void delete(final Long id);

  Transfer transferMoney(@Valid final SendMoneyCommand command);
}
