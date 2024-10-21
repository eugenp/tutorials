package com.baeldung.spring.cloud.aws.sqs.fifo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baeldung.spring.cloud.aws.sqs.fifo.model.entity.Transaction;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

@Service
public class TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private final ConcurrentHashMap<UUID, List<Transaction>> processedTransactions = new ConcurrentHashMap<>();
    private final Set<UUID> failedTransactions = ConcurrentHashMap.newKeySet();

    public void processTransaction(Transaction transaction) {
        logger.info("Processing transaction: {}:{} for account {}", transaction.type(), transaction.amount(), transaction.accountId());
        processedTransactions.computeIfAbsent(transaction.accountId(), k -> new ArrayList<>())
            .add(transaction);
    }

    public List<Transaction> getProcessedTransactionsByAccount(UUID accountId) {
        return processedTransactions.getOrDefault(accountId, new ArrayList<>());
    }

    public void simulateSlowProcessing(Transaction transaction) {
        try {
            processTransaction(transaction);
            Thread.sleep(Double.valueOf(500)
                .intValue());
            logger.info("Transaction processing completed: {}:{} for account {}", transaction.type(), transaction.amount(), transaction.accountId());
        } catch (InterruptedException e) {
            Thread.currentThread()
                .interrupt();
            throw new RuntimeException(e);
        }
    }

    public void processTransactionWithFailure(Transaction transaction) {
        if (!failedTransactions.contains(transaction.transactionId())) {
            failedTransactions.add(transaction.transactionId());
            throw new RuntimeException("Simulated failure for transaction " + transaction.type() + ":" + transaction.amount());
        }
        processTransaction(transaction);
    }
}
