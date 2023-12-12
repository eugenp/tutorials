package com.baeldung.spring.cloud.aws.sqs;

import static com.baeldung.spring.cloud.aws.sqs.BaeldungListeners.CUSTOM_HEADERS_PAYLOAD_QUEUE_NAME;
import static com.baeldung.spring.cloud.aws.sqs.BaeldungListeners.CUSTOM_HEADER_NAME;
import static com.baeldung.spring.cloud.aws.sqs.BaeldungListeners.RECORD_PAYLOAD_QUEUE_NAME;
import static com.baeldung.spring.cloud.aws.sqs.BaeldungListeners.STRING_PAYLOAD_QUEUE_NAME;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.awspring.cloud.sqs.operations.SqsTemplate;

public class SpringCloudAwsSQSLiveTest extends BaseSqsIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringCloudAwsSQSLiveTest.class);

    @Autowired
    SqsTemplate sqsTemplate;

    @Autowired
    CountDownLatchContainer latchContainer;

    @Test
    void givenAStringPayload_whenSend_shouldReceive() throws Exception {
        sqsTemplate.send(to -> to.queue(STRING_PAYLOAD_QUEUE_NAME)
            .payload("My String"));
        logger.info("Sent message");
        assertThat(latchContainer.stringPayloadLatch.await(3, TimeUnit.SECONDS)).isTrue();
    }

    @Test
    void givenARecordPayload_whenSend_shouldReceive() throws Exception {
        sqsTemplate.send(to -> to.queue(RECORD_PAYLOAD_QUEUE_NAME)
            .payload(new BaeldungRecord(1, "My Record")));
        logger.info("Sent message");
        assertThat(latchContainer.recordPayloadLatch.await(3, TimeUnit.SECONDS)).isTrue();
    }

    @Test
    void givenCustomHeaders_whenSend_shouldReceive() throws Exception {
        sqsTemplate.send(to -> to.queue(CUSTOM_HEADERS_PAYLOAD_QUEUE_NAME)
            .payload(new BaeldungRecord(1, "My Record"))
            .headers(Map.of(CUSTOM_HEADER_NAME, "my-header-value")));
        logger.info("Sent message");
        assertThat(latchContainer.customHeadersPayloadLatch.await(3, TimeUnit.SECONDS)).isTrue();
    }

}
