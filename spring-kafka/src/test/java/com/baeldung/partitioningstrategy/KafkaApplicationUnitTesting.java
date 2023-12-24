package com.baeldung.partitioningstrategy;

import static org.junit.Assert.assertEquals;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Assert;
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
@EmbeddedKafka(partitions = 3, brokerProperties = {"listeners=PLAINTEXT://localhost:9092"})
public class KafkaApplicationUnitTesting {
    @Autowired
    private KafkaProducer kafkaProducer;

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
        kafkaProducer.send("default-topic", "message1");
        kafkaProducer.send("default-topic","message2");
        kafkaProducer.send("default-topic","message3");

        Thread.sleep(2000);

        List<ReceivedMessage> records = kafkaMessageConsumer.getReceivedMessages();
        int expectedPartition = records.get(0).getPartition();

        for (ReceivedMessage record : records) {
            if (record.getKey() == null) {
                Assert.assertEquals("Message without key should be in the same partition", expectedPartition, record.getPartition());
            }
        }
    }

    @Test
    void givenProducerWithSameKeyMessages_whenSendingMessages_shouldRouteToSamePartition() throws InterruptedException {
        kafkaProducer.send("order-topic", "partitionA", "critical data");
        kafkaProducer.send("order-topic", "partitionA", "more critical data");
        kafkaProducer.send("order-topic", "partitionB", "another critical message");
        kafkaProducer.send("order-topic", "partitionA", "another more critical data");

        Thread.sleep(1000);

        List<ReceivedMessage> records = kafkaMessageConsumer.getReceivedMessages();
        Map<String, List<ReceivedMessage>> messagesByKey = groupMessagesByKey(records);

        messagesByKey.forEach((key, messages) -> {
            int expectedPartition = messages.get(0).getPartition();
            for (ReceivedMessage message : messages) {
                assertEquals("Messages with key '" + key + "' should be in the same partition",
                    message.getPartition(),
                    expectedPartition);
            }
        });
    }

    @Test
    public void givenProducerWithSameKeyMessages_whenSendingMessages_shouldReceiveInProducedOrder() throws InterruptedException {
        kafkaProducer.send("order-topic", "partitionA", "message1");
        kafkaProducer.send("order-topic", "partitionA", "message3");
        kafkaProducer.send("order-topic", "partitionA", "message4");

        Thread.sleep(1000);

        List<ReceivedMessage> records = kafkaMessageConsumer.getReceivedMessages();

        StringBuilder resultMessage = new StringBuilder();
        records.forEach(record -> resultMessage.append(record.getMessage()));
        String expectedMessage = "message1message3message4";

        assertEquals(
            "Messages with the same key should be received in the order they were produced within a partition",
            expectedMessage,
            resultMessage.toString()
        );
    }

    @Test
    public void givenCustomPartitioner_whenSendingMessages_shouldRouteToCorrectPartition() throws InterruptedException {
        // Configure the producer with the custom partitioner
        KafkaTemplate<String, String> kafkaTemplate = setProducerToUseCustomPartitioner();

        kafkaTemplate.send("order-topic", "123_premium", "Order 123, Premium order message");
        kafkaTemplate.send("order-topic", "456_normal", "Normal order message");

        Thread.sleep(1000);

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
    public void givenCustomPartitioner_whenSendingMessages_shouldConsumeOnlyFromSpecificPartition() throws InterruptedException {
        KafkaTemplate<String, String> kafkaTemplate = setProducerToUseCustomPartitioner();

        kafkaTemplate.send("order-topic", "123_premium", "Order 123, Premium order message");
        kafkaTemplate.send("order-topic", "456_normal", "Normal order message");

        Thread.sleep(1000);

        consumer.assign(Collections.singletonList(new TopicPartition("order-topic", 0)));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

        for (ConsumerRecord<String, String> record : records) {
            assertEquals("Premium order message should be in partition 0", 0, record.partition());
            assertEquals("123_premium", record.key());
        }

        consumer.close();
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
