package com.baeldung.transactional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TransactionalDetectionUnitTest {

    @TestConfiguration
    @SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
    @EnableTransactionManagement
    static class TestConfig {

        @Bean
        public PlatformTransactionManager transactionManager() {
            return new org.springframework.transaction.support.AbstractPlatformTransactionManager() {
                @Override
                protected Object doGetTransaction() {
                    return new Object();
                }

                @Override
                protected void doBegin(Object transaction, org.springframework.transaction.TransactionDefinition definition) {
                }

                @Override
                protected void doCommit(org.springframework.transaction.support.DefaultTransactionStatus status) {
                }

                @Override
                protected void doRollback(org.springframework.transaction.support.DefaultTransactionStatus status) {
                }
            };
        }
    }

    @Test
    @Transactional
    public void givenTransactional_whenCheckingForActiveTransaction_thenReceiveTrue() {
        assertTrue(TransactionSynchronizationManager.isActualTransactionActive());
    }

    @Test
    public void givenNoTransactional_whenCheckingForActiveTransaction_thenReceiveFalse() {
        assertFalse(TransactionSynchronizationManager.isActualTransactionActive());
    }
}
