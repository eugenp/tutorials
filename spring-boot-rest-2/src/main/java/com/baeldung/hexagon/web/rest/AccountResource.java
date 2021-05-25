package com.baeldung.hexagon.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagon.domain.Account;
import com.baeldung.hexagon.service.AccountService;

@RestController
@RequestMapping(path = "/api")
public class AccountResource {

	@Autowired
	private AccountService accountService;

	@GetMapping(path = "/account/{accountNumber}", produces = "application/json")
	public ResponseEntity<Account> getAccountInfo(@PathVariable("accountNumber") String accountNumber) {
		return ResponseEntity.ok(accountService.getAccountInfo(accountNumber));
	}
}
