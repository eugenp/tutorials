package com.baeldung.spring.cloud.aws.sqs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.SqsHeaders;

@Component
public class BaeldungListeners {

    private static final Logger logger = LoggerFactory.getLogger(BaeldungListeners.class);

    public static final String STRING_PAYLOAD_QUEUE_NAME = "string-test-queue";
    public static final String RECORD_PAYLOAD_QUEUE_NAME = "record-test-queue";
    public static final String CUSTOM_HEADERS_PAYLOAD_QUEUE_NAME = "custom-headers-test-queue";
    public static final String CUSTOM_HEADER_NAME = "custom-header";

    final CountDownLatchContainer latchContainer;

    public BaeldungListeners(CountDownLatchContainer latchContainer) {
        this.latchContainer = latchContainer;
    }

    @SqsListener(STRING_PAYLOAD_QUEUE_NAME)
    public void receiveStringMessage(String message) {
        logger.info("Received message: {}", message);
        latchContainer.stringPayloadLatch.countDown();
    }

    @SqsListener(RECORD_PAYLOAD_QUEUE_NAME)
    public void receiveRecordMessage(BaeldungRecord message) {
        logger.info("Received message: {}", message);
        latchContainer.recordPayloadLatch.countDown();
    }

    @SqsListener(queueNames = CUSTOM_HEADERS_PAYLOAD_QUEUE_NAME)
    public void customHeaderMessage(BaeldungRecord message, @Header(CUSTOM_HEADER_NAME) String customHeader,
        @Header(SqsHeaders.MessageSystemAttributes.SQS_APPROXIMATE_FIRST_RECEIVE_TIMESTAMP) Long firstReceive) {
        logger.info("Received message {} with custom header {}. First received at approximately {}.", message, customHeader, firstReceive);
        latchContainer.customHeadersPayloadLatch.countDown();
    }

}
