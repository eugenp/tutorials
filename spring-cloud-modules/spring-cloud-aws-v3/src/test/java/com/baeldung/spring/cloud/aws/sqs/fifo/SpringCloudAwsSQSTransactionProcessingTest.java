package com.baeldung.spring.cloud.aws.sqs.fifo;

import static java.util.function.Predicate.isEqual;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;

import com.baeldung.spring.cloud.aws.sqs.fifo.model.entity.Transaction;
import com.baeldung.spring.cloud.aws.sqs.fifo.model.entity.TransactionType;
import com.baeldung.spring.cloud.aws.sqs.fifo.model.event.TransactionEvent;
import com.baeldung.spring.cloud.aws.sqs.fifo.service.TransactionService;

import io.awspring.cloud.sqs.listener.SqsHeaders;
import io.awspring.cloud.sqs.operations.SqsTemplate;

@ActiveProfiles("fifo")
@SpringBootTest
public class SpringCloudAwsSQSTransactionProcessingTest {

    @Autowired
    private SqsTemplate sqsTemplate;

    @Autowired
    private TransactionService transactionService;

    @Value("${events.queues.fifo.transactions-queue}")
    String transactionsQueue;

    @Value("${events.queues.fifo.slow-queue}")
    String slowQueue;

    @Value("${events.queues.fifo.failure-queue}")
    String failureQueue;

    @Test
    void givenTransactionsFromSameAccount_whenSend_shouldReceiveInOrder() {

        var accountId = UUID.randomUUID();
        var transactions = List.of(createDeposit(accountId, 100.0), createWithdraw(accountId, 50.0), createDeposit(accountId, 25.0));
        var messages = createTransactionMessages(accountId, transactions);

        sqsTemplate.sendMany(transactionsQueue, messages);

        await().atMost(Duration.ofSeconds(5))
            .until(() -> transactionService.getProcessedTransactionsByAccount(accountId), isEqual(eventsToEntities(transactions)));
    }

    @Test
    void givenTransactionsFromDifferentAccounts_whenSend_shouldProcessInParallel() {

        var accountId1 = UUID.randomUUID();
        var accountId2 = UUID.randomUUID();

        var account1Transactions = List.of(createDeposit(accountId1, 100.0), createWithdraw(accountId1, 50.0), createDeposit(accountId1, 25.0));
        var account2Transactions = List.of(createDeposit(accountId2, 50.0), createWithdraw(accountId2, 25.0), createDeposit(accountId2, 50.0));

        var allMessages = Stream.concat(createTransactionMessages(accountId1, account1Transactions).stream(),
                createTransactionMessages(accountId2, account2Transactions).stream())
            .toList();

        sqsTemplate.sendMany(slowQueue, allMessages);

        await().atMost(Duration.ofSeconds(5))
            .until(() -> transactionService.getProcessedTransactionsByAccount(accountId1), isEqual(eventsToEntities(account1Transactions)));

        await().atMost(Duration.ofSeconds(5))
            .until(() -> transactionService.getProcessedTransactionsByAccount(accountId2), isEqual(eventsToEntities(account2Transactions)));
    }

    @Test
    void givenTransactionProcessingFailure_whenSend_shouldRetryInOrder() {

        var accountId = UUID.randomUUID();
        var transactions = List.of(createDeposit(accountId, 100.0), createWithdraw(accountId, 50.0), createDeposit(accountId, 25.0));
        var messages = createTransactionMessages(accountId, transactions);

        sqsTemplate.sendMany(failureQueue, messages);

        await().atMost(Duration.ofSeconds(10))
            .until(() -> transactionService.getProcessedTransactionsByAccount(accountId), isEqual(eventsToEntities(transactions)));
    }

    private List<Message<TransactionEvent>> createTransactionMessages(UUID accountId, Collection<TransactionEvent> transactions) {
        return transactions.stream()
            .map(transaction -> MessageBuilder.withPayload(transaction)
                .setHeader(SqsHeaders.MessageSystemAttributes.SQS_MESSAGE_GROUP_ID_HEADER, accountId.toString())
                .build())
            .toList();
    }

    private List<Transaction> eventsToEntities(List<TransactionEvent> transactionEvents) {
        return transactionEvents.stream()
            .map(TransactionEvent::toEntity)
            .toList();
    }

    private TransactionEvent createWithdraw(UUID accountId1, double amount) {
        return new TransactionEvent(UUID.randomUUID(), accountId1, amount, TransactionType.WITHDRAW);
    }

    private TransactionEvent createDeposit(UUID accountId1, double amount) {
        return new TransactionEvent(UUID.randomUUID(), accountId1, amount, TransactionType.DEPOSIT);
    }

}
