package com.example.quick.hexagonal.services;

import com.example.quick.hexagonal.database.BankAccountRepository;
import com.example.quick.hexagonal.domain.CustomerAccount;
import com.example.quick.hexagonal.log.EventLog;
import com.google.inject.Inject;

public class TransfersService implements Transfers {

  private BankAccountRepository bankAccountRepository;
  private EventLog eventLog;
  private static double MIN_BAL = 10;

  @Inject
  public TransfersService(BankAccountRepository bankAccountRepository, EventLog eventLog) {
    this.bankAccountRepository = bankAccountRepository;
    this.eventLog = eventLog;
  }

  @Override
  public void deposit(CustomerAccount customerAccount, double amount) {
    customerAccount.setBalance(customerAccount.getBalance() + amount);
    bankAccountRepository.updateFunds(customerAccount);
  }

  @Override
  public void withdraw(CustomerAccount customerAccount, double amount) {
    customerAccount.setBalance(customerAccount.getBalance() - amount);
    bankAccountRepository.updateFunds(customerAccount);
  }
}
