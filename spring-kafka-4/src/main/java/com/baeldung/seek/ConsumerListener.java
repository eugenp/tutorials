package com.baeldung.seek;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AbstractConsumerSeekAware;
import org.springframework.stereotype.Component;

@Component
class ConsumerListener extends AbstractConsumerSeekAware {

    public static final Map<String, String> MESSAGES = new HashMap<>();

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        assignments.keySet()
            .forEach(tp -> callback.seekRelative(tp.topic(), tp.partition(), -1, false));
    }

    @KafkaListener(id = "test-seek", topics = "test-seek-topic")
    public void listen(ConsumerRecord<String, String> in) {
        MESSAGES.put(in.key(), in.value());
    }
}
