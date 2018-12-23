package com.example.quick.hexagonal.app;

import com.example.quick.hexagonal.domain.BankServiceImpl;

import java.util.Scanner;

public interface BankingConsoleService {
  void addFunds(BankServiceImpl service, Scanner scanner);
  void withdrawFunds(BankServiceImpl service, Scanner scanner);
  void createAccount(BankServiceImpl service, Scanner scanner);
  void checkBalance(BankServiceImpl service, Scanner scanner);
}
