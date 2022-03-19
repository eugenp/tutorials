package com.baeldung.hexagonal.architecture.example.domain;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.example.model.Account;
import com.baeldung.hexagonal.architecture.example.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountAssembler {

    private final TransactionsAssembler transactionsAssembler;
    private final TransactionRepository transactionRepository;

    public AccountDto toModel(Account entity) {
        // TODO: add rounding for upto two decimal places for balance
        AccountDto dto = new AccountDto(entity.getId(), entity.getBalance());
        dto.setTransactions(transactionsAssembler.toModel(transactionRepository.findAllForAccount(entity.getId())));
        return dto;
    }
}
