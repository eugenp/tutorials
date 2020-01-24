/**
 * 
 */
package com.baeldung.hexagonal.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.core.domain.Account;
import com.baeldung.hexagonal.port.AccountService;

/**
 * @author Dinesh.Rajput
 *
 */
@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping
	public void createAccount(@RequestBody Account account) {
		accountService.createAccount(account);
	}
	
	@GetMapping("/{accountNumber}")
	public Account getAccount(@PathVariable Long accountNumber) {
		return accountService.getAccount(accountNumber);
	}
	
	@GetMapping
	public List<Account> allAccounts() {
		return accountService.allAccounts();
	}
}
