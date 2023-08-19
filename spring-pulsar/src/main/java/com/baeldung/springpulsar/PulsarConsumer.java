package com.baeldung.springpulsar;

import org.apache.pulsar.client.api.DeadLetterPolicy;
import org.apache.pulsar.client.api.SubscriptionType;
import org.apache.pulsar.common.schema.SchemaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.pulsar.annotation.PulsarListener;
import org.springframework.pulsar.listener.AckMode;
import org.springframework.stereotype.Service;

@Service
public class PulsarConsumer {

    private static final String STRING_TOPIC = "string-topic";
    private static final String USER_TOPIC = "user-topic";
    private static final String USER_DEAD_LETTER_TOPIC = "user-dead-letter-topic";
    private final Logger LOGGER = LoggerFactory.getLogger(PulsarConsumer.class);

    @PulsarListener(
      subscriptionName = "string-topic-subscription",
      topics = STRING_TOPIC,
      subscriptionType = SubscriptionType.Shared
    )
    public void stringTopicListener(String str) {
        LOGGER.info("Received String message: {}", str);
    }

    @Bean
    DeadLetterPolicy deadLetterPolicy() {
        return DeadLetterPolicy.builder()
          .maxRedeliverCount(10)
          .deadLetterTopic(USER_DEAD_LETTER_TOPIC)
          .build();
    }

    @PulsarListener(
      subscriptionName = "user-topic-subscription",
      topics = USER_TOPIC,
      subscriptionType = SubscriptionType.Shared,
      schemaType = SchemaType.JSON,
      ackMode = AckMode.RECORD,
      deadLetterPolicy = "deadLetterPolicy",
      properties = {"ackTimeout=60s"}
    )
    public void userTopicListener(User user) {
        LOGGER.info("Received user object with email: {}", user.getEmail());
    }

    @PulsarListener(
      subscriptionName = "dead-letter-topic-subscription",
      topics = USER_DEAD_LETTER_TOPIC,
      subscriptionType = SubscriptionType.Shared
    )
    public void userDlqTopicListener(User user) {
        LOGGER.info("Received user object in user-DLQ with email: {}", user.getEmail());
    }
}
