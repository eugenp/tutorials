package com.baeldung.kafka.message.ordering;

import com.baeldung.kafka.message.ordering.payload.Message;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class MultiPartitionProducer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "com.baeldung.kafka.message.ordering.serialization.JacksonSerializer");

        KafkaProducer<String, Message> producer = new KafkaProducer<>(props);
        for (long insertPosition = 1; insertPosition <= 10 ; insertPosition++) {
            long messageId = Message.getRandomMessageId();
            String key = "Key-" + insertPosition;
            Message message = new Message(insertPosition, messageId);
            producer.send(new ProducerRecord<>("multi_partition_topic", key, message));
            System.out.println("Insert Position: " + message.getInsertPosition() + ", Message Id: " + message.getMessageId());
        }
        producer.close();
        System.out.println("SinglePartitionProducer Completed.");
    }
}
