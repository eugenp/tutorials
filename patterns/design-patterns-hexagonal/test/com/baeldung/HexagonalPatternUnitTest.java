package com.baeldung;

import com.model.Account;
import com.services.AccountRepositoryImpl;
import com.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HexagonalPatternUnitTest {
    private AccountService accountService;
    private Account account;

    @Mock
    private AccountRepositoryImpl accountRepository;


    @BeforeEach
    void setUp(){
        accountRepository = new AccountRepositoryImpl();
        account = new Account();
        account.setAccountNumber(12345678910L);
        account.setBankName("TestBank");
        account.setAccountHolderName("Ted");
        accountService = new AccountService(accountRepository);
    }

    @Test
    void addAccount(){
        accountService.createAccount(account);
        Account accountResponse = accountService.getAccount(12345678910L);
        assertEquals(accountResponse, account);
    }

    @Test
    void createAccount(){
        account.setAccountHolderName("Alex");
        account.setAccountNumber(45678910L);
        account.setBankName("testbank2");
        accountService.createAccount(account);
        assertEquals(account, accountService.getAccount(45678910L));
    }
}
