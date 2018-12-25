package com.baeldung.hexagonal.services;

import com.baeldung.hexagonal.domain.BankAccountRepository;
import com.baeldung.hexagonal.domain.CustomerAccount;
import com.baeldung.hexagonal.log.EventLog;

public class AccountsService implements Accounts {

  private BankAccountRepository bankAccountRepository;

  private EventLog eventLog;

  public AccountsService(BankAccountRepository bankAccountRepository, EventLog eventLog) {
    this.bankAccountRepository = bankAccountRepository;
    this.eventLog = eventLog;
  }

  @Override
  public void createAccount(CustomerAccount customerAccount) {
    bankAccountRepository.createAccount(customerAccount);
    eventLog.logMessage("Account creation successful " + customerAccount.getAccNumber());
  }

  @Override
  public void closeAccount(CustomerAccount customerAccount) {
    return;
  }
}
