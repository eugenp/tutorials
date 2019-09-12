package com.baeldung.hexagonal.loan.console.adapter.services.impl;

import java.util.List;
import java.util.Scanner;

import com.baeldung.hexagonal.loan.console.adapter.services.LoanConsoleService;
import com.baeldung.hexagonal.loan.core.services.LoanCoreService;
import com.baeldung.hexagonal.loan.core.vo.Loan;
import com.google.inject.Inject;

public class LoanConsoleServiceImpl implements LoanConsoleService {

	private LoanCoreService coreService;

	@Inject
	public LoanConsoleServiceImpl(LoanCoreService coreService) {
		this.coreService = coreService;
	}

	public void getAllLoans() {
		List<Loan> loans = coreService.getAllLoans();
		loans.stream().forEach( l -> {
			printLoan(l);
			System.out.println("=======================================================");
		});
	}

	public void addLoan(Scanner scanner) {

		Loan loan = new Loan();

		System.out.println("Enter Designation ::");
		loan.setDesignation(scanner.next());

		System.out.println("Enter Employer Name ::");
		loan.setEmployerName(scanner.next());

		System.out.println("Enter Employer Address ::");
		loan.setEmployerAddress(scanner.next());

		System.out.println("Enter Loan Amount ::");
		String loanAmountStr = scanner.next();
		Double loanAmount = (null == loanAmountStr || "".equals(loanAmountStr)) ? 0L : Double.parseDouble(loanAmountStr);
		loan.setLoanAmount(loanAmount);

		coreService.addLoan(loan);
		System.out.println("Loan Created Successfully!");
	}

	public void deleteLoan(Scanner scanner) {
		System.out.println("Enter the Loan Id you want to delete!");
		String loanIdStr = scanner.next();
		System.out.println("Loan Id"+ loanIdStr);
		Long loanId = (null == loanIdStr || "".equals(loanIdStr)) ? 0L : Long.parseLong(loanIdStr); 
		coreService.deleteLoan(loanId);
		System.out.println("Requested Loan Delted Successfully!");
	}

	public static String getInput(Scanner scanner) {
		return scanner.nextLine();
	}
	
	public static void printLoan(Loan loan) {
		System.out.println("Loan Id: "+loan.getLoanId());
		System.out.println("Designation: "+loan.getDesignation());
		System.out.println("Employer Name: "+loan.getEmployerName());
		System.out.println("Employer Address: "+loan.getEmployerAddress());
		System.out.println("Loan Amount: "+loan.getLoanAmount());
		
	}
}
