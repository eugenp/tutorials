package com.baeldung.springamqp.errorhandling.consumer;

import com.baeldung.springamqp.errorhandling.configuration.SimpleDLQAmqpConfiguration;
import com.baeldung.springamqp.errorhandling.errorhandler.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.baeldung.springamqp.errorhandling.configuration.DLXParkingLotAmqpConfiguration.EXCHANGE_PARKING_LOT;
import static com.baeldung.springamqp.errorhandling.configuration.SimpleDLQAmqpConfiguration.EXCHANGE_MESSAGES;

@Service
public class MessagesConsumer {
    public static final String HEADER_X_RETRIES_COUNT = "x-retries-count";
    public static final int MAX_RETRIES_COUNT = 1;

    private static final Logger log = LoggerFactory.getLogger(MessagesConsumer.class);
    private final RabbitTemplate rabbitTemplate;

    public MessagesConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = SimpleDLQAmqpConfiguration.QUEUE_MESSAGES)
    public void receiveMessage(final Message message) throws BusinessException {
        log.info("Received message: {}", message.toString());
        throw new BusinessException();
    }

    //@RabbitListener(queues = DLXCustomAmqpConfiguration.QUEUE_MESSAGES_DLQ)
    public void processFailedMessages(final Message message) {
        log.info("Received failed message: {}", message.toString());
    }

    //@RabbitListener(queues = QUEUE_MESSAGES_DLQ)
    public void processFailedMessagesRequeue(final Message failedMessage) {
        log.info("Received failed message, requeueing: {}", failedMessage.toString());
        rabbitTemplate.send(EXCHANGE_MESSAGES, failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
    }

    //@RabbitListener(queues = QUEUE_MESSAGES_DLQ)
    public void processFailedMessagesRetryHeaders(final Message failedMessage) {
        Integer retriesCnt = (Integer) failedMessage.getMessageProperties().getHeaders().get(HEADER_X_RETRIES_COUNT);
        if (retriesCnt == null) retriesCnt = 0;
        log.info("Retrying message for the {} time", retriesCnt);
        if (retriesCnt > MAX_RETRIES_COUNT) {
            log.info("Discarding message");
            return;
        }
        failedMessage.getMessageProperties().getHeaders().put(HEADER_X_RETRIES_COUNT, ++retriesCnt);
        rabbitTemplate.send(EXCHANGE_MESSAGES, failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
    }

    // @RabbitListener(queues = QUEUE_MESSAGES_DLQ)
    public void processFailedMessagesRetryWithParkingLot(final Message failedMessage) {
        Integer retriesCnt = (Integer) failedMessage.getMessageProperties().getHeaders().get(HEADER_X_RETRIES_COUNT);
        if (retriesCnt == null) retriesCnt = 0;
        log.info("Retrying message for the {} time", retriesCnt);
        if (retriesCnt > MAX_RETRIES_COUNT) {
            log.info("Sending message to the parking lot queue");
            rabbitTemplate.send(EXCHANGE_PARKING_LOT, failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
            return;
        }
        failedMessage.getMessageProperties().getHeaders().put(HEADER_X_RETRIES_COUNT, ++retriesCnt);
        rabbitTemplate.send(EXCHANGE_MESSAGES, failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
    }

    //@RabbitListener(queues = QUEUE_PARKING_LOT)
    public void processParkingLotQueue(final Message failedMessage) {
        log.info("Received message in parking lot queue");
    }
}