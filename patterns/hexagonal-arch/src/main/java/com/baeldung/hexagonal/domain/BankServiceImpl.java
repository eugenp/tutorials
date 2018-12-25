
package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.log.EventLog;
import com.baeldung.hexagonal.services.Accounts;
import com.baeldung.hexagonal.services.Transfers;

import java.util.Optional;
import java.util.UUID;

public class BankServiceImpl implements BankService {

  private Accounts accounts;

  private Transfers transfers;

  private BankAccountRepository bankAccountRepository;

  private EventLog eventLog;

  private static double MIN_BAL = 10;

  public BankServiceImpl(Accounts accounts, Transfers transfers, BankAccountRepository bankAccountRepository, EventLog eventLog) {
    this.accounts = accounts;
    this.transfers = transfers;
    this.bankAccountRepository = bankAccountRepository;
    this.eventLog = eventLog;
  }

  @Override
  public void createAccount(CustomerAccount customerAccount) {
    // create customer account
    customerAccount.setAccNumber(UUID.randomUUID().toString());
    accounts.createAccount(customerAccount);
  }

  @Override
  public void debit(String accountNumber, double amount) {
    CustomerAccount account = bankAccountRepository.findCustomerAccountByAccNumber(accountNumber);
    // check business rules for debit
    if(Optional.ofNullable(account).isPresent() && account.getBalance() - amount < MIN_BAL) {
      eventLog.logMessage("Insufficient funds!!!");
    } else {
      transfers.withdraw(account, amount);
    }
  }

  @Override
  public void credit(String accountNumber, double amount) {
    CustomerAccount account = bankAccountRepository.findCustomerAccountByAccNumber(accountNumber);
    // check business rules for credit
    if(Optional.ofNullable(account).isPresent()) {
      transfers.deposit(account, amount);
    }
  }

  @Override
  public CustomerAccount getCustomerAccount(String accountNumber) {
    return bankAccountRepository.findCustomerAccountByAccNumber(accountNumber);
  }
}
