package com.baeldung.spring.kafka.managingkafkaconsumergroups;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ManagingConsumerGroupsApplicationKafkaApp.class)
@EmbeddedKafka(partitions = 2, brokerProperties = {"listeners=PLAINTEXT://localhost:9098", "port=9098"}, topics = {"topic1"})
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@ActiveProfiles("managed")
public class ManagingConsumerGroupsIntegrationTest {

    private static final String CONSUMER_1_IDENTIFIER = "org.springframework.kafka.KafkaListenerEndpointContainer#1";
    private static final int TOTAL_PRODUCED_MESSAGES = 5000;
    private static final int MESSAGE_WHERE_CONSUMER_1_LEAVES_GROUP = 10;

    @Autowired
    KafkaTemplate<String, Double> kafkaTemplate;

    @Autowired
    KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    MessageConsumerService consumerService;

    @Test
    public void givenContinuousMessageFlow_whenAConsumerLeavesTheGroup_thenKafkaTriggersPartitionRebalance() throws InterruptedException {
        int currentMessage = 0;

        do {
            kafkaTemplate.send("topic-1", RandomUtils.nextDouble(10.0, 20.0));
            currentMessage++;

            if (currentMessage == MESSAGE_WHERE_CONSUMER_1_LEAVES_GROUP) {
                String containerId = kafkaListenerEndpointRegistry.getListenerContainerIds()
                        .stream()
                        .filter(a -> a.equals(CONSUMER_1_IDENTIFIER))
                        .findFirst()
                        .orElse("");
                MessageListenerContainer container = kafkaListenerEndpointRegistry.getListenerContainer(containerId);
                Thread.sleep(2000);
                Objects.requireNonNull(container).stop();
                kafkaListenerEndpointRegistry.unregisterListenerContainer(containerId);
            }
        } while (currentMessage != TOTAL_PRODUCED_MESSAGES);
        Thread.sleep(2000);

        Set<Integer> partitionsConsumedBy1 = consumerService.consumedPartitions.get("consumer-1");
        Set<Integer> partitionsConsumedBy0 = consumerService.consumedPartitions.get("consumer-0");

        if (!CollectionUtils.isEmpty(partitionsConsumedBy0)) {
            assertEquals(2, partitionsConsumedBy0.size());
        }

        if (!CollectionUtils.isEmpty(partitionsConsumedBy1)) {
            assertEquals(1, partitionsConsumedBy1.size());
        }
    }
}
