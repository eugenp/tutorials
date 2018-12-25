package com.baeldung.hexagonal.services;

import com.baeldung.hexagonal.domain.CustomerAccount;

public interface Accounts {
  void createAccount(CustomerAccount customerAccount);

  void closeAccount(CustomerAccount customerAccount);
}
