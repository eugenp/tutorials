package com.baeldung.loan.inmemory.adapter.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.baeldung.hexagonal.loan.core.repository.LoanRepository;
import com.baeldung.hexagonal.loan.core.vo.Loan;

public class LoanRepositoryImpl implements LoanRepository {

	private Map<Long, Loan> loanMap = new HashMap<Long, Loan>();
	private static AtomicInteger atomicInt = new AtomicInteger(0);

	@Override
	public void addLoan(Loan loan) {
		Long id = getPrimaryKey();
		loan.setLoanId(id);
		loanMap.put(id, loan);
	}

	@Override
	public void deleteLoan(Long loanId) {
		loanMap.remove(loanId);
	}

	@Override
	public List<Loan> getAllLoans() {
		List<Loan> loanList = new ArrayList<>();
		loanMap.values().stream().forEach(c -> loanList.add(c));
		return loanList;
	}

	public static Long getPrimaryKey() {
		return (long) atomicInt.incrementAndGet();
	}
}
