package com.baeldung.springamqp.errorhandling.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static com.baeldung.springamqp.errorhandling.configuration.DLXParkingLotAmqpConfiguration.EXCHANGE_PARKING_LOT;
import static com.baeldung.springamqp.errorhandling.configuration.DLXParkingLotAmqpConfiguration.QUEUE_PARKING_LOT;
import static com.baeldung.springamqp.errorhandling.configuration.SimpleDLQAmqpConfiguration.EXCHANGE_MESSAGES;
import static com.baeldung.springamqp.errorhandling.configuration.SimpleDLQAmqpConfiguration.QUEUE_MESSAGES_DLQ;
import static com.baeldung.springamqp.errorhandling.consumer.MessagesConsumer.HEADER_X_RETRIES_COUNT;

public class ParkingLotDLQAmqpContainer {
    private static final Logger log = LoggerFactory.getLogger(ParkingLotDLQAmqpContainer.class);
    private final RabbitTemplate rabbitTemplate;
    public static final int MAX_RETRIES_COUNT = 2;

    public ParkingLotDLQAmqpContainer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = QUEUE_MESSAGES_DLQ)
    public void processFailedMessagesRetryWithParkingLot(Message failedMessage) {
        Integer retriesCnt = (Integer) failedMessage.getMessageProperties().getHeaders().get(HEADER_X_RETRIES_COUNT);
        if (retriesCnt == null)
            retriesCnt = 1;
        if (retriesCnt > MAX_RETRIES_COUNT) {
            log.info("Sending message to the parking lot queue");
            rabbitTemplate.send(EXCHANGE_PARKING_LOT, failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
            return;
        }
        log.info("Retrying message for the {} time", retriesCnt);
        failedMessage.getMessageProperties().getHeaders().put(HEADER_X_RETRIES_COUNT, ++retriesCnt);
        rabbitTemplate.send(EXCHANGE_MESSAGES, failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
    }

    @RabbitListener(queues = QUEUE_PARKING_LOT)
    public void processParkingLotQueue(Message failedMessage) {
        log.info("Received message in parking lot queue {}", failedMessage.toString());
    }
}
