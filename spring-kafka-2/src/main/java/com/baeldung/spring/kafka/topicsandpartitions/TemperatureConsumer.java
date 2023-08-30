package com.baeldung.spring.kafka.topicsandpartitions;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TemperatureConsumer {

    Map<String, Set<String>> consumedRecords = new ConcurrentHashMap<>();

    @KafkaListener(topics = "celcius-scale-topic", groupId = "group-1")
    public void consumer1(ConsumerRecord<?, ?> consumerRecord) {
        computeConsumedRecord("consumer-1", consumerRecord.partition());
    }

    private void computeConsumedRecord(String key, int consumerRecord) {
        consumedRecords.computeIfAbsent(key, k -> new HashSet<>());
        consumedRecords.computeIfPresent(key, (k, v) -> {
            v.add(String.valueOf(consumerRecord));
            return v;
        });
    }

}
