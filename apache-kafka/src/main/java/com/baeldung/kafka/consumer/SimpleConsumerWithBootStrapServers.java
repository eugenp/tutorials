package com.baeldung.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class SimpleConsumerWithBootStrapServers {

    public static void main(String[] args) {
        try(final Consumer<Long, String> consumer = createConsumer()) {
            ConsumerRecords<Long, String> records = consumer.poll(Duration.ofMinutes(1));
            for(ConsumerRecord<Long, String> record : records) {
                System.out.println(record.value());
            }
        }
    }

    private static Consumer<Long, String> createConsumer() {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092,another-host.com:29092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                "MySampleConsumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        // Create the consumer using props.
        final Consumer<Long, String> consumer = new KafkaConsumer<Long, String>(props);
        // Subscribe to the topic.
        consumer.subscribe(Arrays.asList("samples"));
        return consumer;
    }

}
