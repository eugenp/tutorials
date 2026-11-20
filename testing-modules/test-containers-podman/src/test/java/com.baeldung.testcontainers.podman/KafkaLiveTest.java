package com.baeldung.testcontainers.podman;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaLiveTest {

    @Test
    void whenProducingMessage_thenConsumerReceivesIt() {
        DockerImageName image = DockerImageName.parse("confluentinc/cp-kafka:7.6.1");
        try (KafkaContainer kafka = new KafkaContainer(image)) {
            kafka.start();

            String bootstrap = kafka.getBootstrapServers();
            String topic = "hello";

            Properties prodProps = new Properties();
            prodProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
            prodProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
            prodProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

            try (KafkaProducer<String, String> producer = new KafkaProducer<>(prodProps)) {
                producer.send(new ProducerRecord<>(topic, "key", "hello")).get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Properties consProps = new Properties();
            consProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
            consProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
            consProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
            consProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
            consProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

            try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(consProps)) {
                consumer.subscribe(Collections.singletonList(topic));
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
                ConsumerRecord<String, String> first = records.iterator().next();
                Assertions.assertEquals("hello", first.value());
            }
        }
    }
}
