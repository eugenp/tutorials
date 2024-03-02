package com.baeldung.transactionalandasync;

import jakarta.annotation.Resource;
import org.hibernate.exception.SQLGrammarException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {BankAccountApplication.class}, properties = {
        "spring.jpa.show-sql=true",
        "spring.jpa.properties.hibernate.format_sql=true",
        "spring.jpa.generate-ddl=true",
        "spring.jpa.defer-datasource-initialization=true",
        "spring.sql.init.data-locations=classpath:bank-accounts-data.sql"
})
class AccountServiceIntegrationTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    @Resource
    private AccountService accountService;

    @Test
    void givenPartialFailure_whenTransferringMoney_thenRollbackTransaction() {
        var fromBalanceBeforeTransfer = accountService.getAccountInfo(1L).getBalance();
        var toBalanceBeforeTransfer = accountService.getAccountInfo(2L).getBalance();

        var amount = BigDecimal.valueOf(232.45);

        when(accountRepository.save((new Account(2L, toBalanceBeforeTransfer.add(amount)))))
                .thenThrow(SQLGrammarException.class);

        accountService.transfer(1L, 2L, amount);

        var fromBalanceAfterTransfer = accountService.getAccountInfo(1L).getBalance();
        var toBalanceAfterTransfer = accountService.getAccountInfo(2L).getBalance();

        assertEquals(fromBalanceBeforeTransfer, fromBalanceAfterTransfer);
        assertEquals(toBalanceBeforeTransfer, toBalanceAfterTransfer);
    }
}