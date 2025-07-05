package com.baeldung.spring.kafka.partitioningstrategy;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

@SpringBootTest
@EmbeddedKafka(partitions = 3, brokerProperties = { "listeners=PLAINTEXT://localhost:9095" }, kraft = false)
public class KafkaApplicationIntegrationTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaMessageConsumer kafkaMessageConsumer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private Consumer<String, String> consumer;

    @BeforeEach
    public void clearMessages() {
        kafkaMessageConsumer.clearReceivedMessages();
    }

    @Test
    public void givenDefaultPartitioner_whenSendingMessagesWithoutKey_shouldUseStickyDistribution() throws InterruptedException {
        kafkaTemplate.send("default-topic", "message1");
        kafkaTemplate.send("default-topic", "message2");
        kafkaTemplate.send("default-topic", "message3");

        await().atMost(2, SECONDS)
            .until(() -> kafkaMessageConsumer.getReceivedMessages()
                .size() >= 3);

        List<ReceivedMessage> records = kafkaMessageConsumer.getReceivedMessages();

        Set<Integer> uniquePartitions = records.stream()
            .map(ReceivedMessage::getPartition)
            .collect(Collectors.toSet());

        assertEquals(1, uniquePartitions.size());
    }

    @Test
    void givenProducerWithSameKeyMessages_whenSendingMessages_shouldRouteToSamePartition() throws InterruptedException {
        kafkaTemplate.send("order-topic", "partitionA", "critical data");
        kafkaTemplate.send("order-topic", "partitionA", "more critical data");
        kafkaTemplate.send("order-topic", "partitionB", "another critical message");
        kafkaTemplate.send("order-topic", "partitionA", "another more critical data");

        await().atMost(2, SECONDS)
            .until(() -> kafkaMessageConsumer.getReceivedMessages()
                .size() >= 4);

        List<ReceivedMessage> records = kafkaMessageConsumer.getReceivedMessages();
        Map<String, List<ReceivedMessage>> messagesByKey = groupMessagesByKey(records);

        messagesByKey.forEach((key, messages) -> {
            int expectedPartition = messages.get(0)
                .getPartition();
            for (ReceivedMessage message : messages) {
                assertEquals("Messages with key '" + key + "' should be in the same partition", message.getPartition(), expectedPartition);
            }
        });
    }

    @Test
    public void givenProducerWithSameKeyMessages_whenSendingMessages_shouldReceiveInProducedOrder() throws InterruptedException {
        kafkaTemplate.send("order-topic", "partitionA", "message1");
        kafkaTemplate.send("order-topic", "partitionA", "message3");
        kafkaTemplate.send("order-topic", "partitionA", "message4");

        await().atMost(2, SECONDS)
            .until(() -> kafkaMessageConsumer.getReceivedMessages()
                .size() >= 3);

        List<ReceivedMessage> records = kafkaMessageConsumer.getReceivedMessages();

        StringBuilder resultMessage = new StringBuilder();
        records.forEach(record -> resultMessage.append(record.getMessage()));
        String expectedMessage = "message1message3message4";

        assertEquals("Messages with the same key should be received in the order they were produced within a partition", expectedMessage,
            resultMessage.toString());
    }

    @Test
    public void givenCustomPartitioner_whenSendingMessages_shouldRouteToCorrectPartition() throws InterruptedException {
        // Configure the producer with the custom partitioner
        KafkaTemplate<String, String> kafkaTemplate = setProducerToUseCustomPartitioner();

        kafkaTemplate.send("order-topic", "123_premium", "Order 123, Premium order message");
        kafkaTemplate.send("order-topic", "456_normal", "Normal order message");

        await().atMost(2, SECONDS)
            .until(() -> kafkaMessageConsumer.getReceivedMessages()
                .size() >= 2);

        List<ReceivedMessage> records = kafkaMessageConsumer.getReceivedMessages();

        // Validate that messages are routed to the correct partition based on customer type
        for (ReceivedMessage record : records) {
            if ("123_premium".equals(record.getKey())) {
                assertEquals("Premium order message should be in partition 0", 0, record.getPartition());
            } else if ("456_normal".equals(record.getKey())) {
                assertEquals("Normal order message should be in partition 1", 1, record.getPartition());
            }
        }
    }

    @Test
    public void givenDirectPartitionAssignment_whenSendingMessages_shouldRouteToSpecifiedPartitions() throws Exception {
        kafkaTemplate.send("order-topic", 0, "123_premium", "Premium order message");
        kafkaTemplate.send("order-topic", 1, "456_normal", "Normal order message");

        await().atMost(2, SECONDS)
            .until(() -> kafkaMessageConsumer.getReceivedMessages()
                .size() >= 2);

        List<ReceivedMessage> records = kafkaMessageConsumer.getReceivedMessages();

        for (ReceivedMessage record : records) {
            if ("123_premium".equals(record.getKey())) {
                assertEquals("Premium order message should be in partition 0", 0, record.getPartition());
            } else if ("456_normal".equals(record.getKey())) {
                assertEquals("Normal order message should be in partition 1", 1, record.getPartition());
            }
        }
    }

    @Test
    public void givenCustomPartitioner_whenSendingMessages_shouldConsumeOnlyFromSpecificPartition() throws InterruptedException {
        KafkaTemplate<String, String> kafkaTemplate = setProducerToUseCustomPartitioner();

        kafkaTemplate.send("order-topic", "123_premium", "Order 123, Premium order message");
        kafkaTemplate.send("order-topic", "456_normal", "Normal order message");

        await().atMost(2, SECONDS)
            .until(() -> kafkaMessageConsumer.getReceivedMessages()
                .size() >= 2);

        consumer.assign(Collections.singletonList(new TopicPartition("order-topic", 0)));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

        for (ConsumerRecord<String, String> record : records) {
            assertEquals("Premium order message should be in partition 0", 0, record.partition());
            assertEquals("123_premium", record.key());
        }
    }

    private KafkaTemplate<String, String> setProducerToUseCustomPartitioner() {
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker.getBrokersAsString());
        producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producerProps.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());
        DefaultKafkaProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(producerProps);

        return new KafkaTemplate<>(producerFactory);
    }

    private Map<String, List<ReceivedMessage>> groupMessagesByKey(List<ReceivedMessage> messages) {
        return messages.stream()
            .filter(message -> message.getKey() != null)
            .collect(Collectors.groupingBy(ReceivedMessage::getKey));
    }
}
