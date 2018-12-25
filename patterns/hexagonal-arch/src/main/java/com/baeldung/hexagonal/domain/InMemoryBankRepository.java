package com.baeldung.hexagonal.domain;

import com.baeldung.hexagonal.log.EventLog;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryBankRepository implements BankAccountRepository {

  private static Map<String, CustomerAccount> accounts = new HashMap<>();

  private EventLog eventLog;

  public InMemoryBankRepository(EventLog eventLog) {
    this.eventLog = eventLog;
  }

  @Override
  public CustomerAccount findCustomerAccountByAccNumber(String accountNumber) {
    if(Optional.ofNullable(accounts.get(accountNumber)).isPresent()) {
      return accounts.get(accountNumber);
    } else {
      return null;
    }
  }

  @Override
  public void createAccount(CustomerAccount customerAccount) {
    // check if there is existing account with the same account number else create
    if(!Optional.ofNullable(accounts.get(customerAccount.getAccNumber())).isPresent()) {
      accounts.put(customerAccount.getAccNumber(), customerAccount);
      eventLog.accountCreated();
      return;
    }
    eventLog.logMessage("Account creation error!!");
  }

  @Override
  public void updateAccount(CustomerAccount customerAccount) {
    if (Optional.ofNullable(accounts.get(customerAccount.getAccNumber())).isPresent()) {
      accounts.put(customerAccount.getAccNumber(), customerAccount);
    } else {
      eventLog.logMessage("Unsuccessful update of customer account!!");
    }
  }
}
