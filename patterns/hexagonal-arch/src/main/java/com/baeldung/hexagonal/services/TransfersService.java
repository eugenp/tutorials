package com.baeldung.hexagonal.services;

import com.baeldung.hexagonal.domain.BankAccountRepository;
import com.baeldung.hexagonal.domain.CustomerAccount;
import com.baeldung.hexagonal.log.EventLog;

public class TransfersService implements Transfers {

  private BankAccountRepository bankAccountRepository;

  private EventLog eventLog;
  private static double MIN_BAL = 10;

  public TransfersService(BankAccountRepository bankAccountRepository, EventLog eventLog) {
    this.bankAccountRepository = bankAccountRepository;
    this.eventLog = eventLog;
  }

  @Override
  public void deposit(CustomerAccount customerAccount, double amount) {
    customerAccount.setBalance(customerAccount.getBalance() + amount);
    bankAccountRepository.updateAccount(customerAccount);
  }

  @Override
  public void withdraw(CustomerAccount customerAccount, double amount) {
    customerAccount.setBalance(customerAccount.getBalance() - amount);
    bankAccountRepository.updateAccount(customerAccount);
  }
}
