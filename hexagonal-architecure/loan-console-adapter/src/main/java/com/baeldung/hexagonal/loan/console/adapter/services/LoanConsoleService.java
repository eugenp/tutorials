package com.baeldung.hexagonal.loan.console.adapter.services;

import java.util.Scanner;

public interface LoanConsoleService {

	public void addLoan(Scanner scanner);

	public void deleteLoan(Scanner scanner);

	public void getAllLoans();
}
