package com.baeldung.hexagonal_architecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.baeldung.hexagonal_architecture.service.AccountService;
import com.baeldung.hexagonal_architecture.service_impl.AccountServiceImpl;

public class AccountServiceUnitTest {
    
    static final AccountService accountService = new AccountServiceImpl();
    
    @AfterAll
    static void dataBackToInitialState() {
        accountService.depositAmount(1, 100);
        accountService.withdrawAmount(1, 10000);
    }

    @Test
    void testWithdrawFunctionality() {
        assertTrue(accountService.withdrawAmount(1, 100));
        assertEquals(accountService.getAccountBalance(1), 9900);
    }
    
    @Test
    void testDepositFunctionality() {
        assertTrue(accountService.depositAmount(2, 10000));
        assertEquals(accountService.getAccountBalance(2), 110000);
    }
}
