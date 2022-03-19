package com.baeldung.hexagonal.architecture.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.baeldung.hexagonal.architecture.example.ExampleApplication;
import com.baeldung.hexagonal.architecture.example.domain.AccountDto;
import com.baeldung.hexagonal.architecture.example.domain.TransactionResponse;
import com.baeldung.hexagonal.architecture.example.model.Account;
import com.baeldung.hexagonal.architecture.example.model.Transaction;
import com.baeldung.hexagonal.architecture.example.repository.AccountRepository;
import com.baeldung.hexagonal.architecture.example.repository.TransactionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@SpringBootTest(classes = ExampleApplication.class)
@AutoConfigureMockMvc
public class AccountStatementLiveTest {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionController transactionController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenRequestedAccountStatementAtEndpoint_thenProvideAccountStatement() throws Exception {
        accountRepository.save(getAccount1());
        accountRepository.save(getAccount2());
        transactionRepository.save(getTransaction());
        String uri = "/api/account";
        MvcResult result = mockMvc.perform(get(uri + "/" + "1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
        JsonNode responseJSON = mapper.readTree(result.getResponse()
            .getContentAsString());

        AccountDto responseAccountStatement = new Gson().fromJson(responseJSON.toString(), AccountDto.class);

        Assertions.assertThat(responseAccountStatement.getAccountNumber())
            .isEqualTo(1L);
        Assertions.assertThat(responseAccountStatement.getAccountBalance()
            .compareTo(getExpectedAccountStatement().getAccountBalance()) == 0);
        Assertions.assertThat(responseAccountStatement.getTransactions()
                .get(0)
                .getAmount()
                .compareTo(getExpectedAccountStatement().getTransactions()
                    .get(0)
                    .getAmount()))
            .isEqualTo(0);
        Assertions.assertThat(responseAccountStatement.getTransactions()
                .get(0)
                .getSourceAccountNo())
            .isEqualTo(getExpectedAccountStatement().getTransactions()
                .get(0)
                .getSourceAccountNo());
        Assertions.assertThat(responseAccountStatement.getTransactions()
                .get(0)
                .getTargetAccountNo())
            .isEqualTo(getExpectedAccountStatement().getTransactions()
                .get(0)
                .getTargetAccountNo());
    }

    Account getAccount1() {
        Account account1 = new Account();
        account1.setId(1L);
        account1.setBalance(new BigDecimal("1000"));
        return account1;
    }

    Account getAccount2() {
        Account account2 = new Account();
        account2.setId(2L);
        account2.setBalance(new BigDecimal("2000"));
        return account2;
    }

    AccountDto getExpectedAccountStatement() {
        AccountDto accountDto = new AccountDto(1L, new BigDecimal(1000));
        List<TransactionResponse> transactions = new ArrayList<>();
        transactions.add(getExpectedTransactionResponse());
        accountDto.setTransactions(transactions);
        return accountDto;
    }

    TransactionResponse getExpectedTransactionResponse() {
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAmount(new BigDecimal(1));
        transactionResponse.setSourceAccountNo(1L);
        transactionResponse.setTargetAccountNo(2L);
        return transactionResponse;
    }

    Transaction getTransaction() {
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal(1));
        transaction.setSourceAccount(getAccount1());
        transaction.setTargetAccount(getAccount2());
        return transaction;
    }

}