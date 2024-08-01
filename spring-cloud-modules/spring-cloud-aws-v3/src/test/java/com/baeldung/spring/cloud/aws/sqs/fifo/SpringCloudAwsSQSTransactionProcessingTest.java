package com.baeldung.spring.cloud.aws.sqs.fifo;

import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;

import com.baeldung.spring.cloud.aws.sqs.BaseSqsLiveTest;
import com.baeldung.spring.cloud.aws.sqs.fifo.model.entity.TransactionType;
import com.baeldung.spring.cloud.aws.sqs.fifo.model.event.TransactionEvent;
import com.baeldung.spring.cloud.aws.sqs.fifo.service.TransactionService;
import com.baeldung.spring.cloud.aws.sqs.fifo.configuration.TransactionEventsQueuesProperties;

import io.awspring.cloud.sqs.listener.SqsHeaders;
import io.awspring.cloud.sqs.operations.SqsTemplate;

@ActiveProfiles("fifo")
@SpringBootTest
public class SpringCloudAwsSQSTransactionProcessingTest extends BaseSqsLiveTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringCloudAwsSQSTransactionProcessingTest.class);

    @Autowired
    private SqsTemplate sqsTemplate;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionEventsQueuesProperties queuesProperties;

    @Test
    void givenTransactionsFromSameAccount_whenSend_shouldReceiveInOrder() {

        // given
        var accountId = UUID.randomUUID();
        var transactionId1 = UUID.randomUUID();
        var transactionId2 = UUID.randomUUID();
        var transactionId3 = UUID.randomUUID();

        var transaction1 = new TransactionEvent(transactionId1, accountId, 100.0, TransactionType.DEPOSIT);
        var transaction2 = new TransactionEvent(transactionId2, accountId, 50.0, TransactionType.WITHDRAW);
        var transaction3 = new TransactionEvent(transactionId3, accountId, 200.0, TransactionType.DEPOSIT);

        var messages = createTransactionMessages(accountId, transaction1, transaction2, transaction3);

        // when
        sqsTemplate.sendMany(queuesProperties.getTransactionsQueue(), messages);

        logger.info("Messages sent with transaction IDs: {}, {}, {}", transactionId1, transactionId2, transactionId3);

        // then
        await().atMost(Duration.ofSeconds(5))
            .until(() -> {
                var processedTransactions = transactionService.getProcessedTransactionsByAccount(accountId);
                return processedTransactions.equals(Arrays.asList(transaction1.toEntity(), transaction2.toEntity(), transaction3.toEntity()));
            });
    }

    private List<Message<TransactionEvent>> createTransactionMessages(UUID accountId, TransactionEvent... transactions) {
        return Stream.of(transactions)
            .map(transaction -> MessageBuilder.withPayload(transaction)
                .setHeader(SqsHeaders.MessageSystemAttributes.SQS_MESSAGE_GROUP_ID_HEADER, accountId.toString())
                .build())
            .toList();
    }

    @Test
    void givenTransactionsFromDifferentAccounts_whenSend_shouldProcessInParallel() {
        // given
        var accountId1 = UUID.randomUUID();
        var accountId2 = UUID.randomUUID();

        var transaction1 = new TransactionEvent(UUID.randomUUID(), accountId1, 100.0, TransactionType.DEPOSIT);
        var transaction2 = new TransactionEvent(UUID.randomUUID(), accountId1, 50.0, TransactionType.WITHDRAW);
        var transaction3 = new TransactionEvent(UUID.randomUUID(), accountId1, 100.0, TransactionType.DEPOSIT);

        var transaction4 = new TransactionEvent(UUID.randomUUID(), accountId2, 50.0, TransactionType.WITHDRAW);
        var transaction5 = new TransactionEvent(UUID.randomUUID(), accountId2, 100.0, TransactionType.DEPOSIT);
        var transaction6 = new TransactionEvent(UUID.randomUUID(), accountId2, 50.0, TransactionType.WITHDRAW);

        var messagesAccount1 = createTransactionMessages(accountId1, transaction1, transaction2, transaction3);
        var messagesAccount2 = createTransactionMessages(accountId2, transaction4, transaction5, transaction6);

        var allMessages = Stream.concat(messagesAccount1.stream(), messagesAccount2.stream())
            .toList();

        // when
        sqsTemplate.sendMany(queuesProperties.getSlowQueue(), allMessages);

        // then
        logger.info("Messages sent for accounts: {}, {}", accountId1, accountId2);
        await().atMost(Duration.ofSeconds(5))
            .until(() -> {
                var processedTransactionsAccount1 = transactionService.getProcessedTransactionsByAccount(accountId1);
                var processedTransactionsAccount2 = transactionService.getProcessedTransactionsByAccount(accountId2);
                return processedTransactionsAccount1.equals(Arrays.asList(transaction1.toEntity(), transaction2.toEntity(), transaction3.toEntity()))
                    && processedTransactionsAccount2.equals(Arrays.asList(transaction4.toEntity(), transaction5.toEntity(), transaction6.toEntity()));
            });
    }

    @Test
    void givenTransactionProcessingFailure_whenSend_shouldRetryInOrder() {
        // given
        var accountId = UUID.randomUUID();
        var transactionId1 = UUID.randomUUID();
        var transactionId2 = UUID.randomUUID();

        var transaction1 = new TransactionEvent(transactionId1, accountId, 100.0, TransactionType.DEPOSIT);
        var transaction2 = new TransactionEvent(transactionId2, accountId, 50.0, TransactionType.WITHDRAW);

        var messages = createTransactionMessages(accountId, transaction1, transaction2);

        // when
        sqsTemplate.sendMany(queuesProperties.getFailureQueue(), messages);

        logger.info("Messages sent with transaction IDs: {}, {}", transactionId1, transactionId2);

        // then
        await().atMost(Duration.ofSeconds(10))
            .until(() -> {
                var processedTransactions = transactionService.getProcessedTransactionsByAccount(accountId);
                return processedTransactions.equals(List.of(transaction1.toEntity(), transaction2.toEntity()));
            });
    }
}
