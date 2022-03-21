package com.baeldung.hexagonal.architecture.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.architecture.example.domain.AccountDto;
import com.baeldung.hexagonal.architecture.example.service.AccountService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Api(tags = "Account")
@RequestMapping("/api/account")
public class AccountController {

	private final AccountService accountService;

	@GetMapping("/{accountNo}")
	@ApiOperation(value = "Get Account Statement")
	public AccountDto getAccountInfo(@PathVariable Long accountNo) throws Exception {
		return accountService.getAccountInfo(accountNo);
	}
}