package com.example.quick.hexagonal.services;

import com.example.quick.hexagonal.domain.CustomerAccount;

public interface Transfers {
  void deposit(CustomerAccount customerAccount, double amount);
  void withdraw(CustomerAccount customerAccount, double amount);
}
