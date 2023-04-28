package com.baeldung.kafka.headers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaMessageHeaders {

    private static String TOPIC = "baeldung";
    private static String MESSAGE_KEY = "message";
    private static String MESSAGE_VALUE = "Hello World";
    private static String HEADER_KEY = "website";
    private static String HEADER_VALUE = "baeldung.com";

    private static KafkaProducer<String, String> producer;

    public static void main(String[] args) {
        setup();

        publishMessageWithCustomHeaders();
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
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        producer = new KafkaProducer<>(properties);
    }

}
