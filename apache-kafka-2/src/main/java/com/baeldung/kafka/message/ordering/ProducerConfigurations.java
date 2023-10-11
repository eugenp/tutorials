package com.baeldung.kafka.message.ordering;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ProducerConfigurations {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("max.in.flight.requests.per.connection", "1");
        props.put("batch.size", "16384");
        props.put("linger.ms", "5");
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 10; i++) {
            String key = "Key-" + (i % 3);  // Assuming 3 partitions
            producer.send(new ProducerRecord<>("multi_partition_topic", key, "Message-" + i));
        }

        producer.close();
        System.out.println("MultiPartitionProducer Completed.");
    }
}
