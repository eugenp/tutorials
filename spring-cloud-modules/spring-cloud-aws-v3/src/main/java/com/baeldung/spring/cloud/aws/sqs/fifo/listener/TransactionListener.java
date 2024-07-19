package com.baeldung.spring.cloud.aws.sqs.fifo.listener;

import org.springframework.stereotype.Component;
import com.baeldung.spring.cloud.aws.sqs.fifo.model.event.TransactionEvent;
import com.baeldung.spring.cloud.aws.sqs.fifo.service.TransactionService;
import io.awspring.cloud.sqs.annotation.SqsListener;

@Component
public class TransactionListener {
    private final TransactionService transactionService;

    public TransactionListener(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @SqsListener("${events.queues.fifo.transactions-queue}")
    public void processTransaction(TransactionEvent transactionEvent) {
        transactionService.processTransaction(transactionEvent.toEntity());
    }

    @SqsListener("${events.queues.fifo.slow-queue}")
    public void processParallelTransaction(TransactionEvent transactionEvent) {
        transactionService.simulateSlowProcessing(transactionEvent.toEntity());
    }

    @SqsListener(value = "${events.queues.fifo.failure-queue}", messageVisibilitySeconds = "1")
    public void retryFailedTransaction(TransactionEvent transactionEvent) {
        transactionService.processTransactionWithFailure(transactionEvent.toEntity());
    }

}
