/**
 * 
 */
package com.baeldung.hexagonal.core.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.core.domain.Account;
import com.baeldung.hexagonal.port.AccountRepository;
import com.baeldung.hexagonal.port.AccountService;

/**
 * @author Dinesh.Rajput
 *
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public void createAccount(Account account) {
		accountRepository.createAccount(account);
	}
	
	@Override
	public Account getAccount(Long accountNumber) {
		return accountRepository.getAccount(accountNumber);
	}
	
	@Override
	public List<Account> allAccounts() {
		return accountRepository.allAccounts();
	}

}
