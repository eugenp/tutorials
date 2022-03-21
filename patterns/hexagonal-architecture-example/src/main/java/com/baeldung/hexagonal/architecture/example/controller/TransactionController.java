package com.baeldung.hexagonal.architecture.example.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.architecture.example.domain.TransactionRequest;
import com.baeldung.hexagonal.architecture.example.domain.TransactionResponse;
import com.baeldung.hexagonal.architecture.example.service.TransactionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Api(tags = "Transaction")
@RequestMapping(value = "/api/transaction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

	private final TransactionService transactionService;

	@PostMapping
	@ApiOperation(value = "Make a transaction")
	public TransactionResponse updateBalance(@RequestBody @Valid TransactionRequest request) throws Exception {
		TransactionResponse transactionDto = transactionService.makeTransaction(request);
		return transactionDto;
	}
}