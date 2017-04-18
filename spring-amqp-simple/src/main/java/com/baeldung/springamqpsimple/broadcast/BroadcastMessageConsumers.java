package com.baeldung.springamqpsimple.broadcast;

import com.baeldung.springamqpsimple.MessageConsumer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class BroadcastMessageConsumers {
    private static final Logger logger = LogManager.getLogger(MessageConsumer.class);

    @RabbitListener(queues = {BroadcastConfig.fanoutQueue1Name})
    public void receiveMessageFromFanout1(String message) {
        logger.info("Received fanout 1 message: " + message);
    }

    @RabbitListener(queues = {BroadcastConfig.fanoutQueue2Name})
    public void receiveMessageFromFanout2(String message) {
        logger.info("Received fanout 2 message: " + message);
    }

    @RabbitListener(queues = {BroadcastConfig.topicQueue1Name})
    public void receiveMessageFromTopic1(String message) {
        logger.info("Received topic 1 message: " + message);
    }

    @RabbitListener(queues = {BroadcastConfig.topicQueue2Name})
    public void receiveMessageFromTopic2(String message) {
        logger.info("Received topic 2 message: " + message);
    }
}
