package com.baeldung.kafka.message.ordering;

import com.baeldung.kafka.message.ordering.payload.Message;
import com.baeldung.kafka.message.ordering.serialization.JacksonSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;

import java.util.Properties;

public class MultiPartitionProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JacksonSerializer.class.getName());
        KafkaProducer<Long, Message> producer = new KafkaProducer<>(props);
        for (long partitionKey = 1; partitionKey <= 10 ; partitionKey++) {
            long applicationIdentifier = Message.getRandomApplicationIdentifier();
            Message message = new Message(partitionKey, applicationIdentifier);
            producer.send(new ProducerRecord<>("multi_partition_topic", partitionKey, message));
            System.out.println("Partition Key: " + message.getPartitionKey() + ", Application Identifier: " + message.getApplicationIdentifier());
        }
        producer.close();
        System.out.println("SinglePartitionProducer Completed.");
    }
}
