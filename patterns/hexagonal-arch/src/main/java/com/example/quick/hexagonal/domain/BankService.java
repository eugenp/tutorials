package com.example.quick.hexagonal.domain;

public interface BankService {
  void createAccount(CustomerAccount customerAccount);

  void debit(String accountNumber, double amount);

  void credit(String accountNumber, double amount);

  CustomerAccount getCustomerAccount(String accountNumber);
}
