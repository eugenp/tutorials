package com.example.quick.hexagonal.services;

import com.example.quick.hexagonal.domain.CustomerAccount;

public interface Accounts {
  void createAccount(CustomerAccount customerAccount);
  void closeAccount(CustomerAccount customerAccount);
}
