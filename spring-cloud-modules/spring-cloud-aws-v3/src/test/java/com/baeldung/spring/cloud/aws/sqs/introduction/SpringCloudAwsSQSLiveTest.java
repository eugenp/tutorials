package com.baeldung.spring.cloud.aws.sqs.introduction;

import static com.baeldung.spring.cloud.aws.sqs.introduction.UserEventListeners.EVENT_TYPE_CUSTOM_HEADER;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.awspring.cloud.sqs.operations.SqsTemplate;

@ActiveProfiles("introduction")
@SpringBootTest
public class SpringCloudAwsSQSLiveTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringCloudAwsSQSLiveTest.class);

    @Autowired
    private SqsTemplate sqsTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventQueuesProperties eventQueuesProperties;

    @Test
    void givenAStringPayload_whenSend_shouldReceive() {
        // given
        var userName = "Albert";

        // when
        sqsTemplate.send(to -> to.queue(eventQueuesProperties.getUserCreatedByNameQueue())
            .payload(userName));
        logger.info("Message sent with payload {}", userName);

        // then
        await().atMost(Duration.ofSeconds(3))
            .until(() -> userRepository.findByName(userName)
                .isPresent());
    }

    @Test
    void givenARecordPayload_whenSend_shouldReceive() {
        // given
        String userId = UUID.randomUUID()
            .toString();
        var payload = new UserCreatedEvent(userId, "John", "john@baeldung.com");

        // when
        sqsTemplate.send(to -> to.queue(eventQueuesProperties.getUserCreatedRecordQueue())
            .payload(payload));

        // then
        logger.info("Message sent with payload: {}", payload);
        await().atMost(Duration.ofSeconds(3))
            .until(() -> userRepository.findById(userId)
                .isPresent());
    }

    @Test
    void givenCustomHeaders_whenSend_shouldReceive() {
        // given
        String userId = UUID.randomUUID()
            .toString();
        var payload = new UserCreatedEvent(userId, "John", "john@baeldung.com");
        var headers = Map.<String, Object> of(EVENT_TYPE_CUSTOM_HEADER, "UserCreatedEvent");

        // when
        sqsTemplate.send(to -> to.queue(eventQueuesProperties.getUserCreatedEventTypeQueue())
            .payload(payload)
            .headers(headers));

        // then
        logger.info("Sent message with payload {} and custom headers: {}", payload, headers);
        await().atMost(Duration.ofSeconds(3))
            .until(() -> userRepository.findById(userId)
                .isPresent());
    }

}
