package com.baeldung.kafka.headers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaMessageHeaders {

    private static Logger logger = LoggerFactory.getLogger(KafkaMessageHeaders.class);

    private static String TOPIC = "baeldung";
    private static String MESSAGE_KEY = "message";
    private static String MESSAGE_VALUE = "Hello World";
    private static String HEADER_KEY = "website";
    private static String HEADER_VALUE = "baeldung.com";

    private static KafkaProducer<String, String> producer;
    private static KafkaConsumer<String, String> consumer;

    public static void main(String[] args) {
        setup();

        publishMessageWithCustomHeaders();

        consumeMessageWithCustomHeaders();
    }

    private static void consumeMessageWithCustomHeaders() {
        consumer.subscribe(Arrays.asList(TOPIC));

        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMinutes(1));
        for (ConsumerRecord<String, String> record : records) {
            logger.info(record.key());
            logger.info(record.value());

            Headers headers = record.headers();
            for (Header header : headers) {
                logger.info(header.key());
                logger.info(new String(header.value()));
            }
        }
    }

    private static void publishMessageWithCustomHeaders() {
        List<Header> headers = new ArrayList<>();
        headers.add(new RecordHeader(HEADER_KEY, HEADER_VALUE.getBytes()));

        ProducerRecord<String, String> record1 = new ProducerRecord<>(TOPIC, null, MESSAGE_KEY, MESSAGE_VALUE, headers);
        producer.send(record1);

        ProducerRecord<String, String> record2 = new ProducerRecord<>(TOPIC, null, System.currentTimeMillis(), MESSAGE_KEY, MESSAGE_VALUE, headers);
        producer.send(record2);
    }

    private static void setup() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "ConsumerGroup1");

        producer = new KafkaProducer<>(producerProperties);
        consumer = new KafkaConsumer<>(consumerProperties);
    }

}
