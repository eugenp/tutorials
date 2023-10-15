package com.baeldung.kafka.message.ordering;

import com.baeldung.kafka.message.ordering.payload.Message;
import com.baeldung.kafka.message.ordering.serialization.JacksonDeserializer;
import com.baeldung.kafka.message.ordering.serialization.JacksonSerializer;
import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class SinglePartitionIntegrationTest {
    private static String TOPIC = "single_partition_topic";
    private static int PARTITIONS = 1;
    private static short REPLICATION_FACTOR = 1;
    private static Admin admin;
    private static KafkaProducer<Long, Message> producer;
    private static KafkaConsumer<Long, Message> consumer;

    private static final Duration TIMEOUT_WAIT_FOR_MESSAGES = Duration.ofMillis(5000);

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @BeforeAll
    static void setup() throws ExecutionException, InterruptedException {
        KAFKA_CONTAINER.addExposedPort(9092);

        Properties adminProperties = new Properties();
        adminProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());

        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonSerializer.class.getName());

        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(Config.CONSUMER_VALUE_DESERIALIZER_SERIALIZED_CLASS, Message.class);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        admin = Admin.create(adminProperties);
        producer = new KafkaProducer<>(producerProperties);
        consumer = new KafkaConsumer<>(consumerProperties);
        List<NewTopic> topicList = new ArrayList<>();
        NewTopic newTopic = new NewTopic(TOPIC, PARTITIONS, REPLICATION_FACTOR);
        topicList.add(newTopic);
        CreateTopicsResult result = admin.createTopics(topicList);
        KafkaFuture<Void> future = result.values().get(TOPIC);
        future.whenComplete((voidResult, exception) -> {
            if (exception != null) {
                System.err.println("Error creating the topic: " + exception.getMessage());
            } else {
                System.out.println("Topic created successfully!");
            }
        }).get();
    }

    @AfterAll
    static void destroy() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    void givenASinglePartition_whenPublishedToKafkaAndConsumed_thenCheckForMessageOrder() throws ExecutionException, InterruptedException {
        List<Message> sentMessageList = new ArrayList<>();
        List<Message> receivedMessageList = new ArrayList<>();
        for (long partitionKey = 1; partitionKey <= 10 ; partitionKey++) {
            long applicationIdentifier = Message.getRandomApplicationIdentifier();
            Message message = new Message(partitionKey, applicationIdentifier);
            ProducerRecord<Long, Message> producerRecord = new ProducerRecord<>(TOPIC, partitionKey, message);
            Future<RecordMetadata> future = producer.send(producerRecord);
            sentMessageList.add(message);
            RecordMetadata metadata = future.get();
            System.out.println("Partition : " + metadata.partition());
        }

        consumer.subscribe(Collections.singletonList(TOPIC));
        ConsumerRecords<Long, Message> records = consumer.poll(TIMEOUT_WAIT_FOR_MESSAGES);
        records.forEach(record -> {
            Message message = record.value();
            receivedMessageList.add(message);
        });
        boolean result = true;
        for (int count = 0; count <= 9 ; count++) {
            Message sentMessage = sentMessageList.get(count);
            Message receivedMessage = receivedMessageList.get(count);
            if (!sentMessage.equals(receivedMessage) && result){
                result = false;
            }
        }
        assertTrue(result);
    }
}
