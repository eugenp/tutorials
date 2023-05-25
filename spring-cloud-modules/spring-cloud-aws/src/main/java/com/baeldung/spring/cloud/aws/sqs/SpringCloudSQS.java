package com.baeldung.spring.cloud.aws.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@Lazy
public class SpringCloudSQS {

    private static final Logger logger = LoggerFactory.getLogger(SpringCloudSQS.class);

    static final String QUEUE_NAME = "spring-cloud-test-queue";

    /*
     * CountDownLatch is added to wait for messages
     * during integration test
     */
    CountDownLatch countDownLatch;

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Autowired
    QueueMessagingTemplate queueMessagingTemplate;

    @SqsListener(QUEUE_NAME)
    public void receiveMessage(String message, @Header("SenderId") String senderId) {
        logger.info("Received message: {}, having SenderId: {}", message, senderId);
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    public void send(String queueName, Object message) {
        queueMessagingTemplate.convertAndSend(queueName, message);
    }
}
