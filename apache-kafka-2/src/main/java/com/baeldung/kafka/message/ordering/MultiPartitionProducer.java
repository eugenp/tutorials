package com.baeldung.kafka.message.ordering;

import com.baeldung.kafka.message.ordering.payload.UserEvent;
import com.baeldung.kafka.message.ordering.serialization.JacksonSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Properties;
import java.util.UUID;

public class MultiPartitionProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonSerializer.class.getName());
        KafkaProducer<Long, UserEvent> producer = new KafkaProducer<>(props);
        for (long count = 1; count <= 10 ; count++) {
            UserEvent userEvent = new UserEvent(UUID.randomUUID().toString());
            userEvent.setEventNanoTime(System.nanoTime());
            producer.send(new ProducerRecord<>("multi_partition_topic", count, userEvent));
            System.out.println("Process message with Event ID: " + userEvent.getUserEventId());
        }
        producer.close();
        System.out.println("SinglePartitionProducer Completed.");
    }
}
