package com.baeldung.hexagonal.loan.core.repository;

import java.util.List;

import com.baeldung.hexagonal.loan.core.vo.Loan;

public interface LoanRepository {
	public void addLoan(Loan loan);

	public void deleteLoan(Long loanId);

	public List<Loan> getAllLoans();
}
