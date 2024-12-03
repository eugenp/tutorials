package com.baeldung.kafka.commitoffset;

import com.baeldung.kafka.commitoffset.config.KafkaConfigProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class SpecificOffsetCommit {
    public static void main(String[] args) {

        KafkaConsumer<Long, String> consumer = new KafkaConsumer<>(KafkaConfigProperties.getProperties());
        consumer.subscribe(KafkaConfigProperties.getTopic());
        Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
        int messageProcessed = 0;
        while (true) {
            ConsumerRecords<Long, String> messages = consumer.poll(Duration.ofSeconds(10));
            for (ConsumerRecord<Long, String> message : messages) {
                // processed message
                messageProcessed++;
                currentOffsets.put(new TopicPartition(message.topic(), message.partition()), new OffsetAndMetadata(message.offset() + 1));
                if (messageProcessed % 50 == 0) {
                    consumer.commitSync(currentOffsets);
                }
            }
        }
    }
}
