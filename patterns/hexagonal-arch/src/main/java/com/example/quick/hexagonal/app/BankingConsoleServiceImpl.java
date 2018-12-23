/**
 * Copyright 2018 Expedia Group. All rights reserved. EXPEDIA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.example.quick.hexagonal.app;

import com.example.quick.hexagonal.domain.BankServiceImpl;
import com.example.quick.hexagonal.domain.CustomerAccount;

import java.util.Scanner;

public class BankingConsoleServiceImpl implements BankingConsoleService {

  @Override
  public void addFunds(BankServiceImpl service, Scanner scanner) {
    String accountNumber;
    Double funds;
    System.out.println("Enter your Account number:");
    accountNumber = (readString(scanner));
    System.out.println("Enter funds to be added:");
    funds = Double.parseDouble(readString(scanner));
    service.credit(accountNumber, funds);
  }

  @Override
  public void withdrawFunds(BankServiceImpl service, Scanner scanner) {
    String accountNumber;
    Double funds;
    System.out.println("Enter your Account number:");
    accountNumber = (readString(scanner));
    System.out.println("Enter funds to be withdrawn:");
    funds = Double.parseDouble(readString(scanner));
    service.debit(accountNumber, funds);
  }

  @Override
  public void createAccount(BankServiceImpl service, Scanner scanner) {
    System.out.println("Welcome to open account section");
    System.out.println("Enter the details as asked:");
    CustomerAccount account = new CustomerAccount();

    System.out.println("Enter your name:");
    account.setName(readString(scanner));

    System.out.println("Enter your email address:");
    account.setEmailAddress(readString(scanner));

    System.out.println("Enter initial funds:");
    account.setBalance(Double.parseDouble(readString(scanner)));

    service.createAccount(account);
  }

  @Override
  public void checkBalance(BankServiceImpl service, Scanner scanner) {
    // fetch account and return balance
    String accountNumber;
    System.out.println("Enter your Account number:");
    accountNumber = (readString(scanner));

    CustomerAccount account = service.getCustomerAccount(accountNumber);
    if(account != null) {
      System.out.println("Your account balance is " + account.getBalance());
    } else {
      System.out.println("Account not found");
    }
  }

  private static String readString(Scanner scanner) {
    System.out.print("> ");
    return scanner.next();
  }

}
