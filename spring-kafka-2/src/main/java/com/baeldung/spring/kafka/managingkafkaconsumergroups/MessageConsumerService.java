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

    Map<String, Set<Integer>> consumedRecords = new ConcurrentHashMap<>();

    @KafkaListener(topics = "topic-1", groupId = "group-1")
    public void consumer0(ConsumerRecord<?, ?> consumerRecord) {
        trackConsumedRecords("consumer-0", consumerRecord);
    }

    @KafkaListener(topics = "topic-1", groupId = "group-1")
    public void consumer1(ConsumerRecord<?, ?> consumerRecord) {
        trackConsumedRecords("consumer-1", consumerRecord);
    }

    private void trackConsumedRecords(String key, ConsumerRecord<?, ?> record) {
        consumedRecords.computeIfAbsent(key, k -> new HashSet<>());
        consumedRecords.computeIfPresent(key, (k, v) -> {
            v.add(record.partition());
            return v;
        });
    }
}
