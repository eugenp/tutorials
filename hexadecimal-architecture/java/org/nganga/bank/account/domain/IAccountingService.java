package org.nganga.bank.account.domain;

public interface IAccountingService {
	public Account getAccount();

	public Double getBalance(int accountId);
}
