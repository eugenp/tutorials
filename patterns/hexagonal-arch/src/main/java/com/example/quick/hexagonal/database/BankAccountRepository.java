package com.example.quick.hexagonal.database;

import com.example.quick.hexagonal.domain.CustomerAccount;

public interface BankAccountRepository {

  CustomerAccount findCustomerAccountByAccNumber(String accountNumber);

  void createAccount(CustomerAccount customerAccount);

  void updateFunds(CustomerAccount customerAccount);

}
