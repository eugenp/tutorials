package com.baeldung.hexagonal.architecture.example.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.hexagonal.architecture.example.domain.TransactionRequest;
import com.baeldung.hexagonal.architecture.example.model.Account;
import com.baeldung.hexagonal.architecture.example.repository.TransactionRepository;
import com.baeldung.hexagonal.architecture.example.service.AccountService;
import com.baeldung.hexagonal.architecture.example.service.TransactionService;

@WebMvcTest(controllers = { TransactionController.class }, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE))
class TransactionControllerIntegrationTest {

    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Captor
    private ArgumentCaptor<TransactionRequest> requestCaptor;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .build();

    }

    @Test
    void whenCorrectlyFormattedTransactionRequested_thenAcceptCorrectFormatRequest() throws Exception {
        String payload = "{\n" + "    \"amount\": \"1\",\n" + "    \"details\": \"dummyDetails\",\n" + "    \"referenceNo\": \"123\",\n" + "    \"sourceAccountNo\": \"1\",\n" + "    \"targetAccountNo\": \"2\"\n" + "}";
        mockMvc.perform(post("/api/transaction").content(payload)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void whenTransactionIncorrectlyFormatted_thenFailOnWrongIdentType() throws Exception {
        String payload = "{\n" + "    \"amount\": \"1\",\n" + "    \"details\": \"dummyDetails\",\n" + "    \"referenceNo\": \"123\",\n" + "    \"sourceAccountNo\": \"1\",\n" + "    \"targetAccountNo\": \"WRONG\"\n" + "}";
        mockMvc.perform(post("/api/transaction").content(payload)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    void givenBusinessRulesSatisfied_whenTransactionRequested_thenCallRelevantMethod() throws Exception {
        Mockito.when(accountService.getAccount(1L))
            .thenReturn(getAccount1());
        Mockito.when(accountService.getAccount(2L))
            .thenReturn(getAccount2());

        String payload = "{\n" + "    \"amount\": \"1\",\n" + "    \"details\": \"dummyDetails\",\n" + "    \"referenceNo\": \"123\",\n" + "    \"sourceAccountNo\": \"1\",\n" + "    \"targetAccountNo\": \"2\"\n" + "}";
        this.mockMvc.perform(post("/api/transaction").content(payload)
                .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
        Mockito.verify(transactionService, Mockito.times(1))
            .makeTransaction(requestCaptor.capture());
    }

    Account getAccount1() {
        Account account1 = new Account();
        account1.setId(1L);
        account1.setBalance(new BigDecimal("1000"));
        return account1;
    }

    Account getAccount2() {
        Account account1 = new Account();
        account1.setId(2L);
        account1.setBalance(new BigDecimal("2000"));
        return account1;
    }
}