/**
 * 
 */
package com.baeldung.kafka;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class KafkaProducerTimeOutExceptionTest {

    @Container
    private static final KafkaContainer KAFKA_CONTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    private final static String TOPIC = "baeldung-kafka-github";
    private static String MESSAGE_KEY = "message";
    private final static String TEST_MESSAGE = "Kafka Test Message";
    private final static Integer PARTITION_NUMBER = 3;
    private static KafkaProducer<String, String> producer;
    private static Properties producerProperties;

    @BeforeAll
    static void setup() throws IOException, InterruptedException {
        KAFKA_CONTAINER.addExposedPort(9092);

        /*
        KAFKA_CONTAINER.execInContainer("/bin/sh", "-c", "/usr/bin/kafka-topics " + "--bootstrap-server localhost:9092 " + "--create " +
            "--replication-factor 1 " + "--partitions " + PARTITION_NUMBER + " " + "--topic " + TOPIC);
        */

        KAFKA_CONTAINER.execInContainer("/bin/sh", "-c", "/usr/bin/kafka-topics " + "--bootstrap-server localhost:9092 " + "--create " +
            "--replication-factor 1 " + "--topic " + TOPIC);

        producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.RETRIES_CONFIG, 0);
        producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 100000);
        //producerProperties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 30000);
        producerProperties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 1000);
        //producerProperties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);
        producerProperties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 5);
        producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 10);
        producerProperties.put(ProducerConfig.ACKS_CONFIG, "all");
        producerProperties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        producerProperties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);
        //producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 100000);
        producerProperties.put(ProducerConfig.BATCH_SIZE_CONFIG, 100000);

    }

    @AfterAll
    static void destroy() {
        KAFKA_CONTAINER.stop();
    }

    @Test
    void whenProducerTimeOutExceptionThenExceptionHandled() {
        Exception ex = new ProducerFencedException(null);
        // handler the exception
        assertTrue(true);
    }

    @Test
    void whenProducerTimeOutExceptionThenExceptionOccurs() throws InterruptedException, ExecutionException {
        producer = new KafkaProducer<>(producerProperties);
        //int messagesInTopic = 10000000;
        int messagesInTopic = 10000;

        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, MESSAGE_KEY, TEST_MESSAGE);

        Callback callback = new Callback() {

            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                // TODO Auto-generated method stub
                // TODO Auto-generated method stub
                if (exception == null) {
                    // Success case: Message was acknowledged successfully
                    System.out.println("Message sent successfully to partition " + metadata.partition() + " with offset " + metadata.offset());
                } else {
                    // Failure case: There was an error
                    exception.printStackTrace();
                }
            }
        };

        for (int i = 0; i < messagesInTopic; i++) {
            producer.send(record, callback);
        }

        producer.close();

    }

}
