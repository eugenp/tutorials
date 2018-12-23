/**
 * Copyright 2018 Expedia Group. All rights reserved. EXPEDIA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.example.quick.hexagonal.app;

import com.example.quick.hexagonal.domain.BankServiceImpl;
import com.example.quick.hexagonal.module.BankModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Scanner;

public class BankingConsoleApp {
  public static void main(String[] args) {

    Injector injector = Guice.createInjector(new BankModule());
    BankServiceImpl service = injector.getInstance( BankServiceImpl.class);
    try (final Scanner scanner = new Scanner(System.in)) {
      boolean exit = false;
      while (!exit) {
        printMainMenu();
        String cmd = readString(scanner);
        BankingConsoleServiceImpl bankingConsoleService = new BankingConsoleServiceImpl();
        if ("1".equals(cmd)) {
          bankingConsoleService.createAccount(service, scanner);
        } else if ("2".equals(cmd)) {
          bankingConsoleService.addFunds(service, scanner);
        } else if ("3".equals(cmd)) {
          bankingConsoleService.withdrawFunds(service, scanner);
        } else if ("4".equals(cmd)) {
          bankingConsoleService.checkBalance(service, scanner);
        } else if ("5".equals(cmd)) {
          exit = true;
        } else {
        }
      }
    }

  }

  private static void printMainMenu() {
    System.out.println("### ABC Bank ###");
    System.out.println("1. Open New Account");
    System.out.println("2. Deposit Funds");
    System.out.println("3. Withdraw Funds");
    System.out.println("4. Get Account balance");
    System.out.println("5. Exit");
  }

  private static String readString(Scanner scanner) {
    System.out.print("> ");
    return scanner.next();
  }
}
