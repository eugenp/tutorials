package com.baeldung.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Repository
public class PaymentRepository {

    private JdbcTemplate jdbcTemplate;
    private PlatformTransactionManager transactionManager;

    public PaymentRepository(
            JdbcTemplate jdbcTemplate,
            PlatformTransactionManager transactionManager
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.transactionManager = transactionManager;
    }

    public void processPayment(long paymentId, long amount) {
        TransactionDefinition definition =
                new DefaultTransactionDefinition();

        TransactionStatus status =
                transactionManager.getTransaction(definition);

        jdbcTemplate.update(
                "insert into payments(id, amount) values (?, ?)",
                paymentId,
                amount
        );

        jdbcTemplate.update(
                "update accounts set balance = balance - ? where id = 1",
                amount
        );

        transactionManager.commit(status);
    }
}
