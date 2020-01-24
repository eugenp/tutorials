/**
 * 
 */
package com.baeldung.hexagonal.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.core.domain.Account;
import com.baeldung.hexagonal.port.AccountRepository;

/**
 * @author Dinesh.Rajput
 *
 */
@Repository
public class AccountRepositoryImpl implements AccountRepository {

private Map<Long, Account> accountDB = new HashMap<Long, Account>();
	
	@Override
	public void createAccount(Account account) {
		accountDB.put(account.getAccountNumber(), account);
	}
	
	@Override
	public Account getAccount(Long accountNumber) {
		return accountDB.get(accountNumber);
	}
	
	@Override
	public List<Account> allAccounts() {
		return accountDB.values().stream().collect(Collectors.toList());
	}

}
