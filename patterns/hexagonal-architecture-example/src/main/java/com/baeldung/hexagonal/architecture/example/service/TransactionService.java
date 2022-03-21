package com.baeldung.hexagonal.architecture.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.example.domain.TransactionRequest;
import com.baeldung.hexagonal.architecture.example.domain.TransactionResponse;
import com.baeldung.hexagonal.architecture.example.domain.TransactionsAssembler;
import com.baeldung.hexagonal.architecture.example.model.Account;
import com.baeldung.hexagonal.architecture.example.model.Transaction;
import com.baeldung.hexagonal.architecture.example.repository.TransactionRepository;
import com.baeldung.hexagonal.architecture.example.service.validation.TransactionRequestValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

	private final TransactionRepository transactionRepository;
	private final TransactionRequestValidator transactionRequestValidator;
	private final TransactionsAssembler transactionsAssembler;
	private final AccountService accountService;

	public TransactionResponse makeTransaction(TransactionRequest request) throws Exception {
		transactionRequestValidator.validateTransactionRequest(request);
		Account sourceAccount = accountService.getAccount(request.getSourceAccountNo());
		Account targetAccount = accountService.getAccount(request.getTargetAccountNo());
		sourceAccount.setBalance(sourceAccount.getBalance()
			.subtract(request.getAmount()));
		targetAccount.setBalance(targetAccount.getBalance()
			.add(request.getAmount()));
		accountService.saveAccounts(List.of(sourceAccount, targetAccount));
		return transactionsAssembler.toModel(createTransaction(request, sourceAccount, targetAccount));
	}

	public Transaction createTransaction(TransactionRequest transactionRequest, Account sourceAccount, Account targetAccount) {
		Transaction newTransaction = new Transaction();
		newTransaction.setSourceAccount(sourceAccount); //take in function parameter to avoid DB conn
		newTransaction.setTargetAccount(targetAccount); //take in function parameter to avoid DB conn
		newTransaction.setAmount(transactionRequest.getAmount());
		newTransaction.setReferenceNo(transactionRequest.getReferenceNo());
		newTransaction.setDetails(transactionRequest.getDetails());
		transactionRepository.save(newTransaction);
		return newTransaction;
	}

}
