package com.baeldung.hexagonal.domain;

public interface BankAccountRepository {

  CustomerAccount findCustomerAccountByAccNumber(String accountNumber);

  void createAccount(CustomerAccount customerAccount);

  void updateAccount(CustomerAccount customerAccount);

}
