package com.baeldung.hexagonal.loan.core.services;

import java.util.List;

import com.baeldung.hexagonal.loan.core.vo.Loan;

public interface LoanCoreService 
{
	
	public void addLoan(Loan loan);

	public void deleteLoan(Long longId);

	public List<Loan> getAllLoans();
    
}