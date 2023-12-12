package com.baeldung.spring.cloud.aws.sqs;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BaeldungListeners {

    private static final Logger logger = LoggerFactory.getLogger(BaeldungListeners.class);

    public static final String STRING_PAYLOAD_QUEUE_NAME = "string-test-queue";
    public static final String RECORD_PAYLOAD_QUEUE_NAME = "record-test-queue";

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

}
