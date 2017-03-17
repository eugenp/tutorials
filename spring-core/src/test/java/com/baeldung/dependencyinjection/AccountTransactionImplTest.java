package com.baeldung.dependencyinjection;

import java.math.BigDecimal;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.injection.consumer.AccountTransaction;
import com.baeldung.injection.consumer.AccountTransferImpl;
import com.baeldung.injection.service.AccountToAccountTransfer;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
@Configuration
@ComponentScan(value = { "com.baeldung.injection" })
public class AccountTransactionImplTest {
    private AnnotationConfigApplicationContext context = null;

    @Bean
    public AccountTransaction getAccountService() {
        return new AccountToAccountTransfer() {

            public void validateTransaction(BigDecimal amount) {
                System.out.println("Mock Service");
            }
        };
    }

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(AccountTransactionImplTest.class);
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }

    @Test
    public void test() {
        AccountTransferImpl app = context.getBean(AccountTransferImpl.class);
        Assert.assertTrue(app.deposit(new BigDecimal("10.00")));
    }
}
