package com.baeldung.hexagon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagon.dao.AccountRepository;
import com.baeldung.hexagon.domain.Account;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account getAccountInfo(String accountNumber) {
		return accountRepository.findAccount(accountNumber);
	}
}
