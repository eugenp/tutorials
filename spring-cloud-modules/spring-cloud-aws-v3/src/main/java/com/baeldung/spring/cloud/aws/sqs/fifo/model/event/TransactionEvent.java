package com.baeldung.spring.cloud.aws.sqs.fifo.model.event;

import java.util.UUID;

import com.baeldung.spring.cloud.aws.sqs.fifo.model.entity.Transaction;
import com.baeldung.spring.cloud.aws.sqs.fifo.model.entity.TransactionType;

public record TransactionEvent(UUID transactionId, UUID accountId, double amount, TransactionType type) {

    public Transaction toEntity() {
        return new Transaction(transactionId, accountId, amount, type);
    }

}
