package com.baeldung.hexagonal.architecture.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.example.domain.AccountAssembler;
import com.baeldung.hexagonal.architecture.example.domain.AccountDto;
import com.baeldung.hexagonal.architecture.example.model.Account;
import com.baeldung.hexagonal.architecture.example.repository.AccountRepository;
import com.baeldung.hexagonal.architecture.example.service.exception.NoDataFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountAssembler accountAssembler;
    private final AccountRepository accountRepository;

    public AccountDto getAccountInfo(Long accountId) {
        Account account = getAccount(accountId);
        return accountAssembler.toModel(account);
    }

    public Account getAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new NoDataFoundException("Account not found with AccountNo: " + accountId));
        return account;
    }

    public void saveAccounts(List<Account> accounts) {
        accountRepository.saveAll(accounts);
    }

}
