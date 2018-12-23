package com.example.quick.hexagonal.services;

import com.example.quick.hexagonal.database.BankAccountRepository;
import com.example.quick.hexagonal.domain.CustomerAccount;
import com.example.quick.hexagonal.log.EventLog;
import com.google.inject.Inject;

public class AccountsService implements Accounts {

  private BankAccountRepository bankAccountRepository;
  private EventLog eventLog;

  @Inject
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
