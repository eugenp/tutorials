package com.baeldung.loan.inmemory.adapter.services;

import java.util.List;

import com.baeldung.hexagonal.loan.core.vo.Loan;

public interface LoanInmemoryService {
	
	public void addLoan(Loan loan);

	public void deleteLoan(Long loanId);

	public List<Loan> getAllLoans();
}
