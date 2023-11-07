package com.baeldung.spring.kafka.managingkafkaconsumergroups;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MessageConsumerService {

    Map<String, Set<Integer>> consumedPartitions = new ConcurrentHashMap<>();

    @KafkaListener(topics = "topic-1", groupId = "group-1")
    public void consumer0(ConsumerRecord<?, ?> consumerRecord) {
        trackConsumedPartitions("consumer-0", consumerRecord);
    }

    @KafkaListener(topics = "topic-1", groupId = "group-1")
    public void consumer1(ConsumerRecord<?, ?> consumerRecord) {
        trackConsumedPartitions("consumer-1", consumerRecord);
    }

    private void trackConsumedPartitions(String key, ConsumerRecord<?, ?> record) {
        consumedPartitions.computeIfAbsent(key, k -> new HashSet<>());
        consumedPartitions.computeIfPresent(key, (k, v) -> {
            v.add(record.partition());
            return v;
        });
    }
}
