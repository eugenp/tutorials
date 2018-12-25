package com.baeldung.hexagonal.services;

import com.baeldung.hexagonal.domain.CustomerAccount;

public interface Transfers {
  void deposit(CustomerAccount customerAccount, double amount);

  void withdraw(CustomerAccount customerAccount, double amount);
}
