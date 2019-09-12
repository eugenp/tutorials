package com.baeldung.hexagonal.loan.core.services.impl;

import java.util.List;

import com.baeldung.hexagonal.loan.core.repository.LoanRepository;
import com.baeldung.hexagonal.loan.core.services.LoanCoreService;
import com.baeldung.hexagonal.loan.core.vo.Loan;
import com.google.inject.Inject;

public class LoanCoreServiceImpl implements LoanCoreService {

	private LoanRepository loanRepository;
	
	@Inject
	public LoanCoreServiceImpl(LoanRepository loanRepository) {
		this.loanRepository = loanRepository;
	}
	
	@Override
	public void addLoan(Loan loan) {
		loanRepository.addLoan(loan);
	}

	@Override
	public void deleteLoan(Long loanId) {
		loanRepository.deleteLoan(loanId);
	}

	@Override
	public List<Loan> getAllLoans() {
		return loanRepository.getAllLoans();
	}

}
