package com.account.service.service;

import com.account.service.domain.Account;
import com.account.service.domain.ErrorCode;
import com.account.service.domain.Transfer;
import com.account.service.repository.AccountRepository;
import com.account.service.service.command.CreateAccountCommand;
import com.account.service.service.command.SendMoneyCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AccountUseCaseImpl implements AccountUseCase {
  private final TransferMoneyUseCase transferMoneyUseCase;
  private final AccountRepository accountRepository;

  @Override
  public Account retrieveAccountById(final Long id) {
    log.info("Retrieve account by id : {}", id);
    return accountRepository.findById(id)
      .orElseThrow(() -> ErrorCode.ACCOUNT_NOT_FOUND.asErrorResult(id));
  }

  @Override
  public List<Account> retrieveAccounts() {
    log.info("Retrieve all accounts ");
    return accountRepository.findAll();
  }

  @Override
  public Account create(final @Valid CreateAccountCommand command) {
    log.info("Create account by command : {}", command);
    return accountRepository.save(AccountServiceConverter.toInsertCommand(command));
  }

  @Override
  public void delete(final Long id) {
    log.info("Delete account by id : {}", id);
    accountRepository.delete(id);
  }

  @Override
  public Transfer transferMoney(final @Valid SendMoneyCommand command) {
    log.info("Transfer money with command : {}", command);
    return transferMoneyUseCase.transferMoney(command);
  }
}
