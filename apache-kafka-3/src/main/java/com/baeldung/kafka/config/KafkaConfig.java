package com.baeldung.kafka.config;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaConfig {

    public static final String BOOTSTRAP_SERVERS = "localhost:9092";

    public static final String CUSTOMER_EVENTS_TOPIC = "customer-events";
    public static final String ORDER_EVENTS_TOPIC = "order-events";
    public static final String SYSTEM_METRICS_TOPIC = "system-metrics";
    public static final String INVENTORY_EVENTS_TOPIC = "inventory-events";
    public static final String PAYMENT_EVENTS_TOPIC = "payment-events";
    public static final String PROCESSED_ORDERS_TOPIC = "processed-orders";

    public static Properties createProducerProperties() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        return props;
    }

    public static Properties createConsumerProperties(String groupId) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return props;
    }

    public static Properties createTransactionalProducerProperties(String transactionalId) {
        Properties props = createProducerProperties();
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionalId);
        return props;
    }
}