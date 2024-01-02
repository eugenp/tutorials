package com.baeldung.kafka.consumer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class CustomKafkaListener implements Runnable {
    private static final Logger log = Logger.getLogger(CustomKafkaListener.class.getName());
    private final String topic;
    private final KafkaConsumer<String, String> consumer;
    private Consumer<String> recordConsumer;


    public CustomKafkaListener(String topic, KafkaConsumer<String, String> consumer) {
        this.topic = topic;
        this.consumer = consumer;
        this.recordConsumer = record -> log.info("received: " + record);
    }

    public CustomKafkaListener(String topic, String bootstrapServers) {
        this(topic, defaultKafkaConsumer(bootstrapServers));
    }

    private static KafkaConsumer<String, String> defaultKafkaConsumer(String boostrapServers) {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "test_group_id");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        return new KafkaConsumer<>(props);
    }

    public CustomKafkaListener onEach(Consumer<String> newConsumer) {
        recordConsumer = recordConsumer.andThen(newConsumer);
        return this;
    }

    @Override
    public void run() {
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            consumer.poll(Duration.ofMillis(100))
              .forEach(record -> recordConsumer.accept(record.value()));
        }
    }

}
