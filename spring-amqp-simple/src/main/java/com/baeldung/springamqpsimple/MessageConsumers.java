package com.baeldung.springamqpsimple;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumers {

    private static final Logger logger = LogManager.getLogger(MessageConsumers.class);

    @RabbitListener(queues = {SpringAmqpConfig.directQueueName})
    public void receiveMessage(String message) {
        logger.info("Received Message: " + message);
    }

    @RabbitListener(queues = {SpringAmqpConfig.fanoutQueue1Name})
    public void receiveMessageFromFanout1(String message) {
        logger.info("Received fanout 1 message: " + message);
    }

    @RabbitListener(queues = {SpringAmqpConfig.fanoutQueue2Name})
    public void receiveMessageFromFanout2(String message) {
        logger.info("Received fanout 2 message: " + message);
    }

    @RabbitListener(queues = {SpringAmqpConfig.topicQueue1Name})
    public void receiveMessageFromTopic1(String message) {
        logger.info("Received topic 1 message: " + message);
    }

    @RabbitListener(queues = {SpringAmqpConfig.topicQueue2Name})
    public void receiveMessageFromTopic2(String message) {
        logger.info("Received topic 2 message: " + message);
    }
}
