package com.baeldung.kafka.message.ordering;

import com.baeldung.kafka.message.ordering.payload.Message;
import com.baeldung.kafka.message.ordering.serialization.JacksonDeserializer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

public class ExtSeqWithTimeWindowConsumer {
    private static final long BUFFER_PERIOD_MS = 5000;
    private static final Duration TIMEOUT_WAIT_FOR_MESSAGES = Duration.ofMillis(100);

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(Config.CONSUMER_VALUE_DESERIALIZER_SERIALIZED_CLASS, Message.class);
        Consumer<String, Message> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("multi_partition_topic"));
        List<Message> buffer = new ArrayList<>();
        long lastProcessedTime = System.nanoTime();
        while (true) {
            ConsumerRecords<String, Message> records = consumer.poll(TIMEOUT_WAIT_FOR_MESSAGES);
            records.forEach(record -> {
                buffer.add(record.value());
            });
            if (System.nanoTime() - lastProcessedTime > BUFFER_PERIOD_MS) {
                processBuffer(buffer);
                lastProcessedTime = System.nanoTime();
            }
        }
    }

    private static void processBuffer(List<Message> buffer) {
        Collections.sort(buffer);
        buffer.forEach(message -> {
            System.out.println("Processing message with Insert Position: " + message.getPartitionKey() + ", Message Id: " + message.getMessageId());
        });
        buffer.clear();
    }
}
