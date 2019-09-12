package com.baeldung.hexagonal.loan.console.adapter;

import java.util.Scanner;

import com.baeldung.hexagonal.loan.console.adapter.configuration.ConsoleModule;
import com.baeldung.hexagonal.loan.console.adapter.services.LoanConsoleService;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Client {
	public static void main(String[] args) {
		printWelcomeMessage();
		printLoanERPMenu();

		Injector inject = Guice.createInjector(new ConsoleModule());
		LoanConsoleService consoleService = inject
				.getInstance(LoanConsoleService.class);
		Scanner scanner = new Scanner(System.in);

		boolean exit = false;
		String userInput = scanner.next();
		while (!exit) {
			if ("1".equals(userInput)) {
				consoleService.addLoan(scanner);
			} else if ("2".equals(userInput)) {
				consoleService.getAllLoans();
			} else if ("3".equals(userInput)) {
				consoleService.deleteLoan(scanner);
			} else if ("4".equals(userInput)) {
				exit = true;
				break;
			}
			printLoanERPMenu();
			userInput = scanner.next();
		}

		// System.out.println(userInput);
		scanner.close();

	}

	public static void printWelcomeMessage() {
		System.out
				.println("=======================================================");
		System.out.println("Welcome to Loan ERP Application!");
		System.out
				.println("=======================================================");
	}

	public static void printLoanERPMenu() {
		System.out
				.println("Please press 1, 2 OR 3 to select your task, or 4 to EXIT.");
		System.out.println("1 - Add Loan");
		System.out.println("2 - Retrive  All Loan Information");
		System.out.println("3 - Delete Loan");
		System.out.println("4 - Exit");
	}
}
