package com.baeldung.countingmessages;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class KafkaCountingMessagesComponent {
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    public static Map<String, Object> props = new HashMap<>();

    @PostConstruct
    public void init(){
        System.out.println(getTotalNumberOfMessagesInATopic("baeldung"));
    }

    public Long getTotalNumberOfMessagesInATopic(String topic){
        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(getProps());
        List<TopicPartition> partitions = consumer.partitionsFor(topic).stream()
                .map(p -> new TopicPartition(topic, p.partition()))
                .collect(Collectors.toList());
        consumer.assign(partitions);
        consumer.seekToEnd(Collections.emptySet());
        Map<TopicPartition, Long> endPartitions = partitions.stream()
                .collect(Collectors.toMap(Function.identity(), consumer::position));
        return partitions.stream().mapToLong(p -> endPartitions.get(p)).sum();
    }

    public Map<String, Object> getProps() {
        if (props.isEmpty()) {
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "20971520");
            props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, "20971520");
        }
        return props;
    }
}
