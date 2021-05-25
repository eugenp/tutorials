package com.baeldung.hexagon.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagon.domain.Account;
import com.baeldung.hexagon.domain.AccountType;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

	private static Map<Integer, Account> accountInfo = new HashMap<>();

	static {
		accountInfo.put(1234, new Account(1234, "Mr. X", 2025.45, AccountType.CURRENT));
		accountInfo.put(5678, new Account(5678, "Mr. Y", 2125.45, AccountType.SAVINGS));
	}

	@Override
	public Account findAccount(String acctNumber) {
		Integer accountNumber = Integer.valueOf(acctNumber);
		if (null != accountInfo.get(accountNumber)) {
			return accountInfo.get(accountNumber);
		}
		return new Account();
	}
}
